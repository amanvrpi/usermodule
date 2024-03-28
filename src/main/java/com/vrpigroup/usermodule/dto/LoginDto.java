package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.annotations.email.ValidEmail;
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

    @Schema(description = "Email Id", example = "example@example.com")
    @NotBlank(message = "Email cannot be blank")
    @ValidEmail(message = "Invalid email format")
    private String email;

    @Schema(description = "Password", example = "ABc@3214", required = true)
    @NotBlank(message = "Password cannot be blank")
    @Password(message = "Invalid password format")
    private String createPassword;
}
