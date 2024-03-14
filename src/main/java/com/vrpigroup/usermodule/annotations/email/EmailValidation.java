package com.vrpigroup.usermodule.annotations.email;

import com.verifalia.api.VerifaliaRestClient;
import org.springframework.stereotype.Component;

@Component
public class EmailValidation {

    VerifaliaRestClient verifalia = new VerifaliaRestClient("amanrashm@gmail.com", "2wYna$WwPm946.F");
    public boolean isEmailValid(String email) {
        try {
            verifalia.getEmailValidations().submit(email);
            System.out.println("Status: " + verifalia.getCredits());
            return true;
        } catch (Exception e) {
//            return false;
            return true;
        }
    }
}