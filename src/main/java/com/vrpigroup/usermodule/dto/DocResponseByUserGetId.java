package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.annotations.email.ValidEmail;
import com.vrpigroup.usermodule.annotations.passwordAnnotation.Password;
import com.vrpigroup.usermodule.annotations.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DocResponseByUserGetId {

    private  Long id;
    @Schema(description = "First Name", example = "Aman", required = true)
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;

    @Schema(description = "Last Name", example = "Raj", required = true)
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;

    @Schema(description = "Father's Name", example = "Raj Kumar", required = true)
    @NotBlank(message = "Father's name cannot be blank")
    @Size(min = 3, max = 50, message = "Father's name must be between 3 and 50 characters")
    private String fathersName;

    @Schema(description = "Gender", example = "Male", required = true)
    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @Schema(description = "Date of Birth", example = "1999-12-12", required = true)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Schema(description = "Phone Number", example = "1234567890", required = true)
    @NotBlank(message = "Phone number cannot be blank")
    @Phone(message = "Invalid phone number")
    private String phoneNumber;

    @Schema(description = "Address", example = "Bihar", required = true)
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address can't exceed 255 characters")
    private String address;

    @Schema(description = "Email Id", example = "example@example.com")
    @NotBlank(message = "Email cannot be blank")
    @ValidEmail(message = "Invalid email format")
    private String email;

    @Schema(description = "Password", example = "ABc@3214", required = true)
    @NotBlank(message = "Password cannot be blank")
    @Password(message = "Invalid password format")
    private String createPassword;

    @Schema(description = "Occupation", example = "Student", required = true)
    @NotBlank(message = "Occupation cannot be blank")
    private String occupation;

    @Schema(description = "Aadhar Card Number", example = "123456789012")
    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhar card format")
    private String aadharCardNumber;


    @Schema(description = "Roles", example = "USER")
    @NotBlank(message = "Roles cannot be blank")
    private String roles;

    private String incomeCert; // Name of income certificate image
    private String profilePic; // Name of profile picture image
    private String aadharBack; // Name of Aadhar card back image
    private String aadharFront; // Name of Aadhar card front image

}
