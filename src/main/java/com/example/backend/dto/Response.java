package com.example.backend.dto;

import lombok.Data;

@Data
public class Response {
     private String message;
     private Integer statusCode;
     private Object data;
}
