package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.annotations.email.ValidEmail;
import com.vrpigroup.usermodule.annotations.passwordAnnotation.Password;
import com.vrpigroup.usermodule.annotations.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
public class UpdateUserDto {

    @Schema(name = "First Name", description = "Full Name", example = "Aman Raj")
    @Size(min = 3, max = 50, message = "Full Name must be between 3 and 50 characters")
    private String firstName;

    @Schema(name = "Last Name", description = "Full Name", example = "Aman Raj")
    private String lastName;

    @Schema(name = "fathersName", description = "Father's Name", example = "Raj Kumar")
    @Size(min = 3, max = 50, message = "Father's name must be between 3 and 50 characters")
    private String fathersName;

    @Schema(name = "Gender", description = "Gemder", example = "Male")
    private String gender;

    @Schema(name = "dateOfBirth", description = "Date of Birth", example = "1999-12-12")
    @Past
    @Column(name = "DOB", nullable = false)
    private LocalDate dateOfBirth;

    @Schema(name = "phoneNumber", description = "Phone Number", example = "1234567890")
    @Phone
    private String phoneNumber;

    @Schema(name = "address", description = "Address", example = "Bihar")
    @Size(max = 255, message = "Address can't exceed 255 characters")
    private String address;

    @Schema(name = "email", description = "Email Id", example = " ")
    @ValidEmail
    private String email;

    @Schema(name = "Create Password", description = "Password", example = "ABc@3214")
    @Password
    private String createPassword;


    @Schema(name = "occupation", description = "Occupation", example = "Student")
    private String occupation;

    @Schema(name = "aadharCardNumber", description = "Aadhar Card Number", example = "123456789012")
    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhar card format")
    private String aadharCardNumber;

}
