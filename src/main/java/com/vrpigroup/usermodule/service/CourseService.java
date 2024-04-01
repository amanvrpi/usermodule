package com.vrpigroup.usermodule.service;

import com.vrpigroup.usermodule.entity.CourseEntity;
import com.vrpigroup.usermodule.entity.UserCourseAssociation;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.exception.CourseNotActiveException;
import com.vrpigroup.usermodule.exception.CourseNotFoundException;
import com.vrpigroup.usermodule.repo.CourseRepository;
import com.vrpigroup.usermodule.repo.UserCourseAssociationRepo;
import com.vrpigroup.usermodule.repo.UserRepository;
import com.vrpigroup.usermodule.annotations.email.EmailValidationServiceImpl;
import com.vrpigroup.usermodule.response.PaymentLinkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    private final UserCourseAssociationRepo userCourseAssociationRepo;
    private final EmailValidationServiceImpl emailValidationService;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public CourseService(UserCourseAssociationRepo userCourseAssociationRepo, EmailValidationServiceImpl emailValidationService, CourseRepository courseRepository, UserRepository userRepository, PaymentService paymentService) {
        this.userCourseAssociationRepo = userCourseAssociationRepo;
        this.emailValidationService = emailValidationService;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    public Optional<CourseEntity> getCourseDetails(Long id) {
        return courseRepository.findById(id);
    }

    public CourseEntity createCourse(CourseEntity courseEntity) {
        return courseRepository.save(courseEntity);
    }

    public String updateCourse(Long id) {
        return courseRepository.findById(id)
                .map(Object::toString)
                .orElse("Course not found");
    }

    public Optional<CourseEntity> deleteCourse(Long id) {
        Optional<CourseEntity> course = courseRepository.findById(id);
        course.ifPresent(courseRepository::delete);
        return course;
    }

    public Iterable<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    public String enrollUserForCourse(Long courseId, Long userId) throws CourseNotFoundException, CourseNotActiveException {
        validateEnrollmentParameters(courseId, userId);
        CourseEntity course = getCachedCourseById(courseId);
        validateCourse(course, courseId);
        validateActiveCourse(course);
        try {
            ResponseEntity<Map<String, String>> paymentResponse = initiatePayment(userId, courseId);
            String paymentLink = Objects.requireNonNull(paymentResponse.getBody()).get("paymentLinkUrl");

            if (paymentResponse.getStatusCode() == HttpStatus.OK) {
                return paymentLink;
            } else {
                LOGGER.error("Error initiating payment: {}", paymentResponse.getBody());
                return "Error initiating payment";
            }
        } catch (Exception e) {
            LOGGER.error("Error enrolling user for course: {}", e.getMessage());
            return "Error enrolling user for course";
        }
    }

    private void sendConfirmationEmail(Long userId, Long courseId) {
        try {
            UserEntity user = getUserDetails(userId);
            String userEmail = user.getEmail();
            CourseEntity course = getCourseDetails(courseId)
                    .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
            emailValidationService.sendConformationMail(userEmail, course.getCourseName());
        } catch (CourseNotFoundException e) {
            LOGGER.error("Error sending confirmation email: {}", e.getMessage());
        }
    }

    private void enrollUser(Long userId, Long courseId) {
        UserCourseAssociation userCourseAssociation = new UserCourseAssociation(null, userId, courseId);
        userCourseAssociationRepo.save(userCourseAssociation);
    }

    private ResponseEntity<Map<String, String>> initiatePayment(Long userId, Long courseId) {
        try {
            UserEntity user = getUserDetails(userId);
            Long coursePrice = getCoursePriceByCourseId(courseId);
            var orderId = Math.toIntExact(userId + courseId + new Random().nextInt(10000));
            if (coursePrice != null && coursePrice > 0) {
                String paymentLinkResponse = String.valueOf(paymentService.createPaymentLink(Long.valueOf(orderId), userId, courseId, user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail()));
                if (paymentLinkResponse != null) {
                    Map<String, String> responseBody = new HashMap<>();
                    responseBody.put("paymentLinkUrl", paymentLinkResponse);
                    responseBody.put("paymentLinkId", "paymentLinkId");
                    return ResponseEntity.ok(responseBody);
                } else {
                    throw new RuntimeException("Payment link response is null");
                }
            } else {
                throw new RuntimeException("Course price is not set or is zero");
            }
        } catch (Exception e) {
            LOGGER.error("Error initiating payment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private UserEntity getUserDetails(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    private void validateEnrollmentParameters(Long courseId, Long userId) {
        if (courseId == null || userId == null) {
            throw new IllegalArgumentException("CourseId and userId cannot be null");
        }
    }

    private CourseEntity getCachedCourseById(Long courseId) throws CourseNotFoundException {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
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

    public Long getCoursePriceByCourseId(Long courseId) throws CourseNotFoundException {
        return Optional.of(courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId)).getPrice()).orElse(0L);
    }
}