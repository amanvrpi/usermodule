package com.vrpigroup.usermodule.service;

import com.razorpay.*;
import com.vrpigroup.usermodule.entity.PaymentDetailsRequest;
import com.vrpigroup.usermodule.repo.CourseRepository;
import com.vrpigroup.usermodule.repo.PaymentDetailsRequestRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public String createPaymentLink(
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
                paymentLinkId = payment.get("id");

                String paymentLinkUrl = payment.get("short_url");
                servletResponse.sendRedirect(paymentLinkUrl);
                LOGGER.info("Payment link created: {}", paymentLinkUrl);
                //String response = "Payment link URL: " + paymentLinkUrl + ", Payment link ID: " + paymentLinkId;
                return paymentLinkUrl;
            } else {
                LOGGER.error("Course price is not greater than 0");
                return "Course price is not greater than 0";
            }
        } catch (RazorpayException | IOException e) {
            LOGGER.error("Error creating payment link: {}", e.getMessage());
            return "Error creating payment link";
        }
    }

    private Long getCoursePrice(Long courseId) {
        return Optional.ofNullable(courseRepository.findById(courseId).get().getPrice()).orElse(0L);
    }

    private void storePaymentDetails(PaymentDetailsRequest paymentDetailsRequest) {
        paymentDetailsRequestRepo.save(paymentDetailsRequest);
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
        String callbackUrl = "https://vrpigroup.com/course/verify-payment"
                + "?orderId=" + orderId
                + "&userId=" + userId
                + "&courseId=" + courseId
                + "&amount=" + (coursePrice * 100);
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


    public Payment verifyPayment(String paymentId, int amount, Long userId, Long courseId, String signature) {
        try {
            Payment payment = razorpay.payments.fetch(paymentId);
            System.out.println(payment);
            if (payment.get("amount").equals(amount)) {
                generateInvoice(amount, userId, courseId);
                PaymentDetailsRequest paymentDetailsRequest = new PaymentDetailsRequest();
                paymentDetailsRequest.setSignature(signature);
                paymentDetailsRequest.setUserId(userId);
                paymentDetailsRequest.setCourseId(courseId);
                paymentDetailsRequest.setPaymentId(paymentId);
                paymentDetailsRequest.setAmount(Long.valueOf(amount));
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

    private void generateInvoice(int amount, Long userId, Long courseId) {
        try {
            JSONObject invoiceRequest = new JSONObject();
            invoiceRequest.put("amount", amount);
            invoiceRequest.put("currency", "INR");
            invoiceRequest.put("type", "link");
            invoiceRequest.put("customer_email", "email");
            invoiceRequest.put("customer_contact", "mobile");
            invoiceRequest.put("description", "Course payment for course ID: " + courseId);
            Invoice invoice = razorpay.invoices.create(invoiceRequest);
            LOGGER.info("Invoice generated successfully: {}", invoice);
        } catch (RazorpayException e) {
            LOGGER.error("Error generating invoice: {}", e.getMessage());
        }
    }
}