package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.annotations.passwordAnnotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Schema(name = "loginDto", description = "Data Transfer Object for login")
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LoginDto {

    @NotBlank(message = "Email is mandatory")
    @Schema(name="email",description = "Email Id",example = "officialkanhaiya121@gmail.com")
    @Email
    private String email;
    @Schema(name = "password", description = "Password", example = "Kanhaiya@123")
    @NotBlank(message = "password is mandatory")
    @Password
    private String password;


}
