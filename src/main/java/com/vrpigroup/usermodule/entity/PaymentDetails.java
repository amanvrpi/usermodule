package com.vrpigroup.usermodule.entity;

public record PaymentDetails(PaymentMethod paymentMethod, PaymentStatus status, String paymentId,
                             String razorpayPaymentLinkId, String razorpayPaymentLinkReferenceId,
                             String razorpayPaymentLinkStatus, String razorpayPaymentId) {

}