package com.vrpigroup.usermodule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponseDto", description = "Data Transfer Object for Error Response")
public class ErrorResponseDto {
    @Schema(name = "appPath", description = "Application Path", example = "/user", required = true)
    private String appPath;
    @Schema(name = "errorCode", description = "Error Code", example = "404", required = true)
    private HttpStatus errorCode;
    @Schema(name = "errorMessage", description = "Error Message", example = "User not found", required = true)
    private String errorMessage;
    @Schema(name = "errorTime", description = "Error Time", example = "2021-12-12T12:12:12", required = true)
    private LocalDateTime errorTime;
}