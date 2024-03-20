package com.vrpigroup.usermodule.service;

import com.vrpigroup.usermodule.entity.PaymentDetailsRequest;
import com.vrpigroup.usermodule.exception.CourseNotActiveException;
import com.vrpigroup.usermodule.annotations.email.EmailValidationServiceImpl;
import com.vrpigroup.usermodule.entity.CourseEntity;
import com.vrpigroup.usermodule.entity.UserCourseAssociation;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.exception.CourseNotFoundException;
import com.vrpigroup.usermodule.repo.CourseRepository;
import com.vrpigroup.usermodule.repo.PaymentDetailsRequestRepo;
import com.vrpigroup.usermodule.repo.UserCourseAssociationRepo;
import com.vrpigroup.usermodule.repo.UserRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;
@Component
public class CourseService {

    private final UserCourseAssociationRepo userCourseAssociationRepo;
    private final EmailValidationServiceImpl emailValidationService;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final PaymentDetailsRequestRepo paymentDetailsRequestRepo;
    private final String paymentUrl = "http://localhost:8081/payment-vrpi/payments/";

    public CourseService(UserCourseAssociationRepo userCourseAssociationRepo, EmailValidationServiceImpl emailValidationService, CourseRepository courseRepository, UserRepository userRepository, PaymentService paymentService, PaymentDetailsRequestRepo paymentDetailsRequestRepo) {
        this.userCourseAssociationRepo = userCourseAssociationRepo;
        this.emailValidationService = emailValidationService;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.paymentDetailsRequestRepo = paymentDetailsRequestRepo;
    }

    public Optional<CourseEntity> getCourseDetails(Long id) {
        return Optional.of(courseRepository.findById(id)).orElseThrow(
                () -> new RuntimeException("Course not found"));
    }

    public CourseEntity createCourse(CourseEntity courseEntity) {
        return Optional.of(courseRepository.save(courseEntity)).orElseThrow(
                () -> new RuntimeException("Course not created"));
    }

    public String updateCourse(Long id) {
        return Optional.of(courseRepository.findById(id)).orElseThrow(
                () -> new RuntimeException("Course not found")).toString();
    }

    public Optional<CourseEntity> deleteCourse(Long id) {
        return Optional.of(courseRepository.findById(id)).orElseThrow(
                () -> new RuntimeException("Course not found"));
    }

    public Object getAllCourses() {
        return Optional.of(courseRepository.findAll()).orElseThrow(
                () -> new RuntimeException("Courses not found"));
    }

    public Boolean enrollUserForCourse(Long courseId, Long userId) throws CourseNotFoundException, CourseNotActiveException {
        validateEnrollmentParameters(courseId, userId);
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(null);
        System.out.println(course.getCourseName());
        validateCourse(course, courseId);
        validateActiveCourse(course);
        ResponseEntity<Boolean> paymentResponse = initiatePayment(userId, courseId);
        if (paymentResponse.getStatusCode() == HttpStatus.OK) {
            enrollUser(userId, courseId);
            sendConfirmationEmail(userId, courseId);
            return true;
        } else {
            throw new RuntimeException("Error initiating payment");
        }
    }

    private void sendConfirmationEmail(Long userId, Long courseId) {
        var userEmail = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")).getEmail();
        var course = courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Course not found"));
        emailValidationService.sendConformationMail(userEmail,course.getCourseName());
    }

    private void enrollUser(Long userId, Long courseId) {
        var userCourseAssociation = new UserCourseAssociation(userId, courseId);
        userCourseAssociationRepo.save(userCourseAssociation);
    }

    private ResponseEntity<Boolean> initiatePayment(Long userId, Long courseId) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        UserEntity user = getUserDetails(userId);
        map.add("userId", userId);
        map.add("courseId", courseId);
        map.add("firstName", user.getFirstName());
        map.add("lastName", user.getLastName());
        map.add("email", user.getEmail());
        map.add("mobile", user.getPhoneNumber());
        var orderId = generateRandomOrderId(userId, courseId);
        paymentService.createPaymentLink(Long.valueOf(orderId),
                userId, courseId, user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(), user.getEmail());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private UserEntity getUserDetails(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    private Integer generateRandomOrderId(Long userId, Long courseId) {
        return Math.toIntExact((userId + courseId + hashCode()));
    }

    private void validateEnrollmentParameters(Long courseId, Long userId) {
        if (courseId == null || userId == null) {
            throw new IllegalArgumentException("CourseId and userId cannot be null");
        }
    }

    private CourseEntity getCachedCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    private void validateCourse(CourseEntity course, Long courseId) throws CourseNotFoundException {
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
    }

    private void validateActiveCourse(CourseEntity course) throws CourseNotActiveException {
        if (!course.getActive()) {
            throw new CourseNotActiveException("Course is not active");
        }
    }

    public Long getCoursePriceByCourseId(Long courseId) {
        return Optional.ofNullable(courseRepository.findById(courseId)
                .get().getPrice()).orElse(0L);
    }

    public void storePaymentDetails(Long orderId, Long userId, Long courseId, String paymentLinkId, String paymentLinkUrl) {
    }
}
