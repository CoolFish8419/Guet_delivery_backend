package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;

@TableName("orders")

@Data
public class Order {
    @TableId(type = IdType.AUTO)
    @TableField(value = "creat_time", fill = FieldFill.INSERT)
    private Integer orderId;
    private Integer publisherUserId;
    private Integer ReceiverUserId;
    private String publisherAddress;
    private String parcelArea;
    private String parcelAddress;
    private Integer distancePubParcel;
    private Integer pickupCode;
    private String remark;
    private Integer status;
    private Float paid;
    private Timestamp requireTime;
    private Timestamp createTime;
    private Timestamp receiveTaskTime;
    private Timestamp takePackageTime;
    private Timestamp arriveTime;
    private Timestamp finishTime;
    private Float score;
    private String accidentMessage;
    private String comment;
    private  Integer pointed;
}
