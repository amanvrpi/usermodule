package com.vrpigroup.usermodule.service;

import com.razorpay.*;

import com.vrpigroup.usermodule.entity.PaymentDetailsRequest;
import com.vrpigroup.usermodule.repo.CourseRepository;
import com.vrpigroup.usermodule.repo.PaymentDetailsRequestRepo;

import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;

@Component
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final RazorpayClient razorpay;
    private final String apiKey = "rzp_test_HDibd0r72mDwz5";
    private final String apiSecret = "AIs9tgYbPT4quUHU8VfMPcGy";
    private final CourseRepository courseRepository;
    private final PaymentDetailsRequestRepo paymentDetailsRequestRepo;
    private final HttpServletResponse servletResponse;
    private String paymentLinkId = "";
    private String paymentLinkUrl = "";

    public PaymentService(
            CourseRepository courseRepository,
            PaymentDetailsRequestRepo paymentDetailsRequestRepo,/*,
            @Value("${razorpay.api.key}") String apiKey,
            @Value("${razorpay.api.secret}") String apiSecret*/HttpServletResponse servletResponse) {
        this.courseRepository = courseRepository;
        this.paymentDetailsRequestRepo = paymentDetailsRequestRepo;
        try {
            this.razorpay = new RazorpayClient(apiKey, apiSecret);
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to initialize Razorpay client", e);
        }
        this.servletResponse = servletResponse;
    }

    public ResponseEntity<String> createPaymentLink(
            @PathVariable Long orderId,
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String mobile,
            @RequestParam String email
    ) {
        try {
            Long coursePrice = getCoursePrice(courseId);
            if (coursePrice > 0) {
                JSONObject paymentLinkRequest = createPaymentLinkRequest(Math.toIntExact(coursePrice), firstName, lastName, mobile, email, orderId, userId, courseId, paymentLinkId, paymentLinkUrl);
                PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
                String paymentLinkId = payment.get("id");
                String paymentLinkUrl = payment.get("short_url");
                servletResponse.sendRedirect(paymentLinkUrl);
                LOGGER.info("Payment link created: {}", paymentLinkUrl);
                String response = "Payment link URL: " + paymentLinkUrl + ", Payment link ID: " + paymentLinkId;
                return ResponseEntity.ok(response);
            } else {
                LOGGER.error("Course price is not greater than 0");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Course price is not greater than 0");
            }
        } catch (RazorpayException | IOException e) {
            LOGGER.error("Error creating payment link: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment link");
        }
    }


    private Long getCoursePrice(Long courseId) {
        return Optional.ofNullable(courseRepository.findById(courseId).get().getPrice()).orElse(0L);
    }

    /*public boolean captureAndVerifyPayment(Long orderId, Long userId, Long courseId, String paymentLinkId, int coursePrice, String paymentLinkUrl) {
        try {
            *//*Payment payment = capturePayment(paymentLinkId, coursePrice, "INR");
            if (payment != null) {*//**//*
                //generateInvoice(payment.get("id"), coursePrice, "INR");*//*
                boolean paymentSuccess = verifyPayment(paymentLinkId);
                if (paymentSuccess) {
                    PaymentDetailsRequest paymentDetailsRequest = new PaymentDetailsRequest();
                    paymentDetailsRequest.setOrderId(orderId);
                    paymentDetailsRequest.setUserId(userId);
                    paymentDetailsRequest.setCourseId(courseId);
                    paymentDetailsRequest.setPaymentLinkId(paymentLinkId);
                    paymentDetailsRequest.setPaymentLinkUrl(paymentLinkUrl);
                    storePaymentDetails(paymentDetailsRequest);
                    paymentDetailsRequestRepo.save(paymentDetailsRequest);
                }
                return paymentSuccess;
            *//*} else {
                LOGGER.error("Failed to capture payment for payment link ID: {}", paymentLinkId);
                return false;
            }*//*
        } catch (Exception e) {
            LOGGER.error("Error capturing and verifying payment: {}", e.getMessage());
            return false;
        }
    }*/


    private void storePaymentDetails(PaymentDetailsRequest paymentDetailsRequest) {
        paymentDetailsRequestRepo.save(paymentDetailsRequest);
    }

    public Invoice generateInvoice(String paymentId, int amount) {
        try {
            JSONObject invoiceRequest = new JSONObject();
            invoiceRequest.put("amount", amount);
            invoiceRequest.put("currency", "INR");
            invoiceRequest.put("payment_id", paymentId);
            Invoice invoice = razorpay.invoices.create(invoiceRequest);
            LOGGER.info("Invoice generated successfully: {}", invoice);
            return invoice;
        } catch (RazorpayException e) {
            LOGGER.error("Error generating invoice: {}", e.getMessage());
            return null;
        }
    }


    private JSONObject createPaymentLinkRequest(int coursePrice, String firstName, String lastName, String mobile, String email, Long orderId, Long userId, Long courseId, String paymentLinkId, String paymentLinkUrl) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", coursePrice * 100);
        paymentLinkRequest.put("currency", "INR");
        JSONObject customer = new JSONObject();
        customer.put("name", firstName + " " + lastName);
        customer.put("contact", mobile);
        customer.put("email", email);
        paymentLinkRequest.put("customer", customer);
        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + 86400000);

        // Constructing the callback URL with dynamic parameters
        String callbackUrl = "https://vrpigroup.com/verify-payment"
                + "?orderId=" + orderId
                + "&userId=" + userId
                + "&courseId=" + courseId
                + "&paymentId=" + paymentLinkId
                + "&amount=" + (coursePrice * 100)
                + "&paymentLinkUrl=" + paymentLinkUrl;
        paymentLinkRequest.put("callback_url", callbackUrl);
        paymentLinkRequest.put("callback_method", "get");
        JSONObject notes = new JSONObject();
        notes.put("orderId", orderId);
        notes.put("userId", userId);
        notes.put("courseId", courseId);
        notes.put("paymentId", paymentLinkId);
        notes.put("email", email);
        notes.put("mobile", mobile);
        notes.put("firstName", firstName);
        notes.put("lastName", lastName);
        paymentLinkRequest.put("notes", notes);
        return paymentLinkRequest;
    }

    public Payment verifyPayment(String paymentId, String orderId, int amount, Long orderIdParam,
                                 Long userId, Long courseId, String paymentLinkId) {
        try {
            Payment payment = razorpay.payments.fetch(paymentId);
            Order order = razorpay.orders.fetch(orderId);
            if (payment.get("amount").equals(amount) && order.get("amount").equals(amount)) {
                generateInvoice(paymentId, amount);
                PaymentDetailsRequest paymentDetailsRequest = new PaymentDetailsRequest();
                paymentDetailsRequest.setOrderId(orderIdParam);
                paymentDetailsRequest.setUserId(userId);
                paymentDetailsRequest.setCourseId(courseId);
                paymentDetailsRequest.setPaymentLinkId(paymentLinkId);
                paymentDetailsRequest.setPaymentLinkUrl(paymentLinkUrl);
                storePaymentDetails(paymentDetailsRequest);
                paymentDetailsRequestRepo.save(paymentDetailsRequest);
                return payment;
            } else {
                return null;
            }
        } catch (RazorpayException e) {
            return null;
        }
    }
}