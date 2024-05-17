package com.example.backend.dto;

import com.example.backend.entity.Chat;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private String message;
    List<Chat> data;
}
