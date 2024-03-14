package com.vrpigroup.usermodule.annotations.phone;

import com.vrpigroup.usermodule.annotations.phone.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone phone) {
    }

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext context) {
        if (phoneField == null) {
            return false;
        }
        String cleanedPhoneNumber = phoneField.replaceAll("\\D", "");
        if (!cleanedPhoneNumber.matches("\\d{10}"))
            return false;
        String formattedPhoneNumber = "+91" + cleanedPhoneNumber;
        return formattedPhoneNumber.length() == 13;
    }
}