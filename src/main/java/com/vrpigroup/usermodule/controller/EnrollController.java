package com.vrpigroup.usermodule.controller;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.vrpigroup.usermodule.exception.CourseNotActiveException;
import com.vrpigroup.usermodule.entity.CourseEntity;
import com.vrpigroup.usermodule.exception.CourseNotFoundException;
import com.vrpigroup.usermodule.service.CourseService;
import com.vrpigroup.usermodule.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class EnrollController {

    private final CourseService courseService;
    private final PaymentService paymentService;
    private final RazorpayClient razorpay;
    private final String apiKey = "rzp_test_HDibd0r72mDwz5";
    private final String apiSecret = "AIs9tgYbPT4quUHU8VfMPcGy";

    public EnrollController(CourseService courseService, PaymentService paymentService, RazorpayClient razorpay) {
        this.courseService = courseService;
        this.paymentService = paymentService;
        try {
            this.razorpay = new RazorpayClient(apiKey, apiSecret);
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to initialize Razorpay client", e);
        }
    }

    // Get course details by ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourseById(@PathVariable Long id) {
        Optional<CourseEntity> courseOptional = courseService.getCourseDetails(id);
        return courseOptional.map(course -> ResponseEntity.ok().body(course))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new course
    @PostMapping("/create-course")
    public ResponseEntity<CourseEntity> createCourse(@RequestBody CourseEntity courseEntity){
        CourseEntity createdCourse = courseService.createCourse(courseEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // Update course details by ID
    @PutMapping("/update-course/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Long id) {
        String result = courseService.updateCourse(id);
        return ResponseEntity.ok(result);
    }

    // Delete course by ID
    @DeleteMapping("/delete-course/{id}")
    public ResponseEntity<CourseEntity> deleteCourse(@PathVariable Long id) {
        Optional<CourseEntity> deletedCourse = courseService.deleteCourse(id);
        return deletedCourse.map(course -> ResponseEntity.ok().body(course))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get details of all courses
    @GetMapping("/all-courses")
    public ResponseEntity<String> getAllCourses() {
        String allCourses = courseService.getAllCourses().toString();
        return ResponseEntity.ok(allCourses);
    }

    @PostMapping("/enroll-course")
    public ResponseEntity<String> enrollCourse(@RequestParam Long courseId, @RequestParam Long userId) {
        try {
            String paymentlink  = courseService.enrollUserForCourse(courseId, userId);
            return ResponseEntity.ok(paymentlink);
        } catch (CourseNotActiveException e) {
            return ResponseEntity.badRequest().body("Course is not active");
        } catch (CourseNotFoundException e) {
            return ResponseEntity.badRequest().body("Course not found");
        }
    }
    //verify-payment
    @GetMapping("/verify-payment")
    public String verifyPayment(
            @RequestParam("razorpay_payment_id") String paymentId,
            @RequestParam("amount") int amount,
            @RequestParam("userId") Long userId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("razorpay_signature") String signature
    ){
        Payment payment = paymentService.verifyPayment(paymentId, amount, userId, courseId, signature);
        if (payment != null) {
            return "Payment successful";
        } else {
            return "Payment failed";
        }
    }
}