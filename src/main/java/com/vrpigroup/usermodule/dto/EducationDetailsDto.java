package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationDetailsDto {

    private Long id;

    @Schema(description = "Education Level", example = "Bachelor's Degree", required = true)
    @NotBlank(message = "Education level is required")
    private String educationLevel;

    @Schema(description = "Degree", example = "Computer Science", required = true)
    @NotBlank(message = "Degree is required")
    private String degree;

    @Schema(description = "Institution Name", example = "XYZ University", required = true)
    @NotBlank(message = "Institution name is required")
    private String institutionName;

    @Schema(description = "Institute Location", example = "City, Country")
    private String instituteLocation;

    @NotNull(message = "Start year is required")
    @Column(name = "start_year", nullable = false)
    @Schema(description = "Start year of education", example = "2020")
    private Integer startYear;

    @NotNull(message = "End year is required")
    @FutureOrPresent(message = "End year must be present or in the future")
    @Column(name = "end_year", nullable = false)
    @Schema(description = "End year of education", example = "2024")
    private Integer endYear;

    @NotNull(message = "Percentage/CGPA is required")
    @Column(name = "percentage_cgpa", nullable = false)
    @Schema(description = "Percentage or CGPA obtained", example = "3.8")
    private Double percentageOrCgpa;

    @ManyToOne
    @JoinColumn(name = "user_id") // Adjust column name if necessary
    private UserEntity user;

    @Schema(description = "Date and time when the educational details were created")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the educational details were last updated")
    private LocalDateTime updatedAt;
}
