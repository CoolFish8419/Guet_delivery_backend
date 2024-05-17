package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.awt.geom.Point2D;
import java.math.BigDecimal;

@Data
@TableName("receiver")
public class Receiver {
    @TableId(type = IdType.AUTO)
    private Integer receiverId;
    private Integer userId;
    private String coordinates;
    private Integer status;
    private Integer finishOrderNumber;
    private Float score;
}
