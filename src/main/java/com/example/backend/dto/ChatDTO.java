package com.example.backend.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatDTO {
    private Integer orderId;
    private Integer fromId;
    private Integer toId;
    private Integer fixed;
    private String message;
}
