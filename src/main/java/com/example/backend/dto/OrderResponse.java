package com.example.backend.dto;

import com.example.backend.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private String message;
    List<Order> data;
}
