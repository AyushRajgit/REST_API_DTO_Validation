package com.cper.REST_API_DTO_Validation.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionDTO {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private Map<String,String> message;
    private String path;

    public ValidationExceptionDTO(LocalDateTime timestamp, Integer status, String error, Map<String, String> message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
