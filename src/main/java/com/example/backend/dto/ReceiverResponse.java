package com.example.backend.dto;

import com.example.backend.entity.Receiver;
import lombok.Data;

import java.util.List;

@Data
public class ReceiverResponse {
    private String message;
    private List<Receiver> data;
}
