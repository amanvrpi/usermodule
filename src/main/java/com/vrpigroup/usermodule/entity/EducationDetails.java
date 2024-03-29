package com.vrpigroup.usermodule.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "education_details")
public class EducationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier for education details", example = "1")
    private Long id;

    @NotNull(message = "Education level is required")
    @Size(min = 1, max = 255, message = "Education level must be between 1 and 255 characters")
    @Column(name = "education_level", nullable = false)
    @Schema(description = "Education level", example = "Bachelor's")
    private String educationLevel;

    @NotNull(message = "Degree is required")
    @Size(min = 1, max = 255, message = "Degree must be between 1 and 255 characters")
    @Column(name = "degree", nullable = false)
    @Schema(description = "Degree obtained", example = "Computer Science")
    private String degree;

    @NotNull(message = "Institution name is required")
    @Size(min = 1, max = 255, message = "Institution name must be between 1 and 255 characters")
    @Column(name = "institution_name", nullable = false)
    @Schema(description = "Name of the institution", example = "University of XYZ")
    private String institutionName;

    @Size(max = 255, message = "Institute location cannot exceed 255 characters")
    @Column(name = "institute_location")
    @Schema(description = "Location of the institution", example = "City, Country")
    private String instituteLocation;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    @Schema(description = "Start date of education", example = "2020-01-01")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date must be present or in the future")
    @Column(name = "end_date")
    @Schema(description = "End date of education", example = "2024-12-31")
    private LocalDate endDate;

    @Size(max = 255, message = "Grade cannot exceed 255 characters")
    @Column(name = "grade")
    @Schema(description = "Grade obtained", example = "A+")
    private String grade;


    @Column(name = "user_id", nullable = false)
    @Schema(description = "ID of the associated user", example = "1")
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @Schema(description = "Creation timestamp", example = "2022-03-20T12:00:00")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Schema(description = "Last update timestamp", example = "2022-03-20T13:30:00")
    private LocalDateTime updatedAt;
}
