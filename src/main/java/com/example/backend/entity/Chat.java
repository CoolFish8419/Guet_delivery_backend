package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("chat")
public class Chat {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer fromId;
    private Integer toId;
    private Timestamp createTime;
    private String message;
}
