package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.annotations.passwordAnnotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Schema(name = "loginDto", description = "Data Transfer Object for login")
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LoginDto {

    @Schema(name="email",description = "Email Id",example = "officialkanhaiya121@gmail.com")
    @Email
    private String email;
    @Schema(name = "password", description = "Password", example = "Kanhaiya@123")
    @Password
    private String password;


}
