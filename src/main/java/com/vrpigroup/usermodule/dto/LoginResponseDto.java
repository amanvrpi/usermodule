package com.vrpigroup.usermodule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

@Schema(name = "ResponseDto", description = "Data Transfer Object for Response")
public class LoginResponseDto {



    public LoginResponseDto(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    private Long id;

    public LoginResponseDto(Long id, String statusCode, String statusMessage) {
        this.id = id;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    private String statusCode;

    private String statusMessage;
}