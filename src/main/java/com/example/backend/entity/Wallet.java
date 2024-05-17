package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("wallet")
@Data
public class Wallet {
    @TableId(type = IdType.AUTO)
    private Integer walletId;
    private Integer userId;
    private Integer payKey;
    private BigDecimal balance;

}
