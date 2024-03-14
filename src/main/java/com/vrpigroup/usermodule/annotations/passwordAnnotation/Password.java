package com.vrpigroup.usermodule.annotations.passwordAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 *
 * @author AMAN RAJ
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a custom annotation for password validation
 * @implNote This annotation is used to validate the password
 * @Conact Us : amanrashm@gmail.com
 * @see PasswordValidator
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "Your password must contain at least one letter, one number, and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}