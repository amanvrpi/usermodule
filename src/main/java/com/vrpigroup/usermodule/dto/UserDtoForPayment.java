package com.vrpigroup.usermodule.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class UserDtoForPayment {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
