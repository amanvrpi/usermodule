package com.vrpigroup.usermodule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@Schema(name = "ResponseDto", description = "Data Transfer Object for Response")
public class ResponseDto {

private  Long userId;

    public ResponseDto(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public ResponseDto(Long userId, String statusCode, String statusMessage) {
        this.userId = userId;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    private String statusCode;

    private String statusMessage;
}