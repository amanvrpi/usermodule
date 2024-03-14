package com.vrpigroup.usermodule.annotations.email;

import org.springframework.scheduling.annotation.Async;

public interface EmailValidationService {
    void sendVerificationEmail(String email);

    @Async
    void sendVerificationEmail(String email, String otp);
}
