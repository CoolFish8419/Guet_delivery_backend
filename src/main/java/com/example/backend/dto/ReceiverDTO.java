package com.example.backend.dto;

import lombok.Data;

import java.awt.geom.Point2D;

@Data
public class ReceiverDTO {
    private Integer receiverId;
    private Integer userId;
    private String coordinates;
    private Integer status;

}
