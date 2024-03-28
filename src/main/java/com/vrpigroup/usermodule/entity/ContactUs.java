package com.vrpigroup.usermodule.entity;

import com.vrpigroup.usermodule.annotations.email.ValidEmail;
import com.vrpigroup.usermodule.annotations.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactUs {
@Schema(name = "id", description = "Id", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Schema(name = "name", description = "Name", example = "Aman Raj", required = true)
    private String name;
@Schema(name = "email", description = "Email Id", example = "")
    @ValidEmail
    @Column(nullable = false)
    private String email;
@Schema(name = "description", description = "Description", example = "I want to know about the product", required = true)
    @Column(nullable = false)
    private String description;
@Schema(name = "phone", description = "Phone Number", example = "1234567890", required = true)
    @Column(nullable = false)
    private String phone;
    @Schema(name = "messagedOn", description = "Messaged On", example = "2021-12-12T12:12:12", required = true)
    @CreatedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime messagedOn;

}
