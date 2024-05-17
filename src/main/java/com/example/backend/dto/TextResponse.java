package com.example.backend.dto;

import com.example.backend.entity.Text;
import lombok.Data;

import java.util.List;
@Data
public class TextResponse {
    private String message;
    List<Text> data;
}
