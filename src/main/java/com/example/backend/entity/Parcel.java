package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.lang.reflect.Array;
import java.sql.Timestamp;

@Data
@TableName("parcel")
public class Parcel {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Double longitude;
    private Double latitude;
    private String receiver;
}
