package com.example.backend.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer user_id;
    private Integer user_type;
    private String username;
    private String open_id;

}
