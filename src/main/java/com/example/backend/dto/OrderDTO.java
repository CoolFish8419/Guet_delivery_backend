package com.example.backend.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderDTO {
    private Integer orderId;
    private Integer publisherUserId;
    private Integer ReceiverUserId;
    private String publisherAddress;
    private Timestamp requireTime;
    private String parcelArea;
    private String parcelAddress;
    private Integer distancePubParcel;
    private Integer pickupCode;
    private String remark;
    private Integer status;
    private Float paid;
    private Integer getPackage;
    private Integer arrive;
    private Integer finish;
    private String accidentMessage;
    private Float score;
    private String comment;
}
