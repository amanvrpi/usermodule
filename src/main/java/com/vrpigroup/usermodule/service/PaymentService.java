package com.vrpigroup.usermodule.service;

import com.razorpay.*;
import com.vrpigroup.usermodule.entity.PaymentDetailsRequest;
import com.vrpigroup.usermodule.repo.CourseRepository;
import com.vrpigroup.usermodule.repo.PaymentDetailsRequestRepo;
import com.vrpigroup.usermodule.response.PaymentLinkResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Component
public class PaymentService {

    private final RazorpayClient razorpay;
    private final CourseRepository courseRepository;
    
    private final PaymentDetailsRequestRepo paymentDetailsRequestRepo;
    //@Value("${razorpay.api.key}")
    private final String apiKey = "rzp_test_Y2wKKNPVZruuwe";

    //@Value("${razorpay.api.secret}")
    private final String apiSecret = "H1GUDvfwvsIeiCJrzcbQLbb1";

    public PaymentService(RazorpayClient razorpay, CourseRepository courseRepository, PaymentDetailsRequestRepo paymentDetailsRequestRepo) {
        this.razorpay = razorpay;
        this.courseRepository = courseRepository;
        this.paymentDetailsRequestRepo = paymentDetailsRequestRepo;
    }

    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable Long orderId,
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String mobile,
            @RequestParam String email
    ) {
        try {
            int coursePrice = Math.toIntExact(getCoursePrice(courseId));
            if (coursePrice != 0) {
                JSONObject paymentLinkRequest = createPaymentLinkRequest(coursePrice, firstName, lastName, mobile, email, courseId);
                PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
                String paymentLinkId = payment.get("id");
                String paymentLinkUrl = payment.get("short_url");
                System.out.println("Payment link created: " + paymentLinkUrl);
                boolean paymentSuccess = captureAndVerifyPayment(orderId, userId, courseId, paymentLinkId, coursePrice, paymentLinkUrl);
                if (paymentSuccess) {
                    PaymentLinkResponse response = new PaymentLinkResponse(paymentLinkUrl, paymentLinkId);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new PaymentLinkResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(new PaymentLinkResponse(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RazorpayException e) {
            return new ResponseEntity<>(new PaymentLinkResponse(),HttpStatus.PAYMENT_REQUIRED);
        }
    }

    private Long getCoursePrice(Long courseId) {
        return Optional.ofNullable(courseRepository.findById(courseId)
                .get().getPrice()).orElse(0L);
    }

    private boolean captureAndVerifyPayment(Long orderId, Long userId, Long courseId, String paymentLinkId, int coursePrice, String paymentLinkUrl) {
        Payment payment = capturePayment(paymentLinkId, coursePrice, "INR");
        generateInvoice(payment.get("id"), coursePrice, "INR");
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
    }

    public boolean verifyPayment(String paymentLinkId) {
        try {
            PaymentLink payment = razorpay.paymentLink.fetch(paymentLinkId);
            var paymentDone = razorpay.payments.capture(paymentLinkId, payment.get("amount"));
            return paymentDone.get("status").equals("captured");
        } catch (RazorpayException e) {
            System.err.println("Error verifying payment: " + e.getMessage());
            return false;
        }
    }

    private void storePaymentDetails(PaymentDetailsRequest paymentDetailsRequest) {
        paymentDetailsRequestRepo.save(paymentDetailsRequest);
    }

    public Invoice generateInvoice(String paymentId, int amount, String currency) {
        try {
            JSONObject invoiceRequest = new JSONObject();
            invoiceRequest.put("amount", amount);
            invoiceRequest.put("currency", currency);
            invoiceRequest.put("payment_id", paymentId);
            Invoice invoice = razorpay.invoices.create(invoiceRequest);
            System.out.println("Invoice generated successfully: " + invoice);
            return invoice;
        } catch (RazorpayException e) {
            System.err.println("Error generating invoice: " + e.getMessage());
            return null;
        }
    }

    public Payment capturePayment(String paymentId, int amount, String currency) {
        try {
            JSONObject paymentRequest = new JSONObject();
            paymentRequest.put("amount", amount);
            paymentRequest.put("currency", currency);
            Payment payment = razorpay.payments.capture(paymentId, paymentRequest);
            System.out.println("Payment captured successfully: " + payment);
            return payment;
        } catch (RazorpayException e) {
            System.err.println("Error capturing payment: " + e.getMessage());
            return null;
        }
    }

    private JSONObject createPaymentLinkRequest(int coursePrice, String firstName, String lastName, String mobile, String email, Long courseId) {
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
        paymentLinkRequest.put("callback_url", "http://localhost:8081/payment-vrpi/payments?course_id=" + courseId);
        paymentLinkRequest.put("callback_method", "get");
        return paymentLinkRequest;
    }
}