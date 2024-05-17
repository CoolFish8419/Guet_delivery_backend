package com.example.backend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private Integer userId;
    private Integer userType;
    private String username;
    private String openId;
    private String avatar;
    private Integer isNew;
    private String address;
    private String telephone;
}
