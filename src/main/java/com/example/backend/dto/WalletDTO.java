package com.example.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDTO {
    private BigDecimal balance;
    private Integer payKey;
    private Integer userId;
    private Integer walletId;

}
