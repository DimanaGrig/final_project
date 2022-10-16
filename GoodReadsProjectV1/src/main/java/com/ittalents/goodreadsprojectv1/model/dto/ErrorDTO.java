package com.ittalents.goodreadsprojectv1.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

    @Data
    public class ErrorDTO {
        private  int status;
        private LocalDateTime time;
        private String massage;

    }

