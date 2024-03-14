package com.vrpigroup.usermodule.entity;

import com.vrpigroup.usermodule.annotations.email.ValidEmail;
import com.vrpigroup.usermodule.annotations.passwordAnnotation.Password;
import com.vrpigroup.usermodule.annotations.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Long id;

    @Schema(name = "First Name", description = "Full Name", example = "Aman Raj", required = true)
    @Size(min = 3, max = 50, message = "Full Name must be between 3 and 50 characters")
    private String firstName;

    @Schema(name = "Last Name", description = "Full Name", example = "Aman Raj", required = true)
    private String lastName;


    @Schema(name = "fathersName", description = "Father's Name", example = "Raj Kumar", required = true)
    @Size(min = 3, max = 50, message = "Father's name must be between 3 and 50 characters")
    private String fathersName;

    @Schema(name = "Gender", description = "Gemder", example = "Male", required = true)
    private String gender;

    @Schema(name = "dateOfBirth", description = "Date of Birth", example = "1999-12-12", required = true)
    @Past
    @Column(name = "DOB", nullable = false)
    private LocalDate dateOfBirth;

    @Schema(name = "phoneNumber", description = "Phone Number", example = "1234567890", required = true)
//    @Phone
    private String phoneNumber;

    @Schema(name = "address", description = "Address", example = "Bihar", required = true)
    @Size(max = 255, message = "Address can't exceed 255 characters")
    private String address;


    @Schema(name = "email", description = "Email Id", example = " ")
//    @ValidEmail
    private String email;

    @Schema(name = "Create Password", description = "Password", example = "ABc@3214", required = true)
    @Password
    private String createPassword;

    @Schema(name = "occupation", description = "Occupation", example = "Student", required = true)
    private String occupation;

    @Schema(name = "aadharCardNumber", description = "Aadhar Card Number", example = "123456789012")
//    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhar card format")
    private String aadharCardNumber;


    @Schema(name = "aadharFront", description = "Aadhar Front", example = "aadhar_front.jpg")
    @Lob
    @Column(length = 5000000)
    private byte[] aadharFront;

    @Schema(name = "aadharBack", description = "Aadhar Back", example = "aadhar_back.jpg")
    @Lob
    @Column(length = 5000000)
    private byte[] aadharBack;

    @Lob
    @Column(length = 5000000)
    @Schema(name = "profilePic", description = "Profile Picture", example = "profile.jpg")
    private byte[] profilePic;

    @Schema(name = "role", description = "Roles", example = "USER")
    @ElementCollection
    private List<String> roles = new ArrayList<>();

    public boolean active;

    private String otp;
}
