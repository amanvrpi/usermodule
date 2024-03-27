package com.vrpigroup.usermodule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Start Date", example = "2020-09-01", required = true)
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Schema(description = "End Date", example = "2024-06-30")
    @FutureOrPresent(message = "End date must be in the present or future")
    private LocalDate endDate;

    @Schema(description = "Grade or GPA", example = "3.5")
    private String grade;

    @Schema(description = "User ID", example = "123", required = true)
    @NotNull(message = "User ID is required")
    private Long userId;

    @Schema(description = "Date and time when the educational details were created")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the educational details were last updated")
    private LocalDateTime updatedAt;
}
