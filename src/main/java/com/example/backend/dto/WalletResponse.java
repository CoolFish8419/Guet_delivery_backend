package com.example.backend.dto;
import com.example.backend.entity.Wallet;
import lombok.Data;

import java.util.List;

@Data
public class WalletResponse {
    private String message;
    private List<Wallet> data;
}
