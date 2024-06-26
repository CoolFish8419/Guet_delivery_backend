package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("text")
public class Text {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;

}
