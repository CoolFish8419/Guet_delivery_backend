package com.example.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.dto.WalletDTO;
import com.example.backend.dto.WalletResponse;
import com.example.backend.entity.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService extends IService<Wallet> {
    WalletResponse Ma_All();

    WalletResponse newWallet(WalletDTO walletDTO);

    WalletResponse update(WalletDTO walletDTO);

    WalletResponse payBill(Integer user_id, Integer pay_key , BigDecimal balance);
    List<Wallet> getBalance(Integer user_id);
    WalletResponse createOrder(Integer user_id, Integer pay_key , BigDecimal balance);


}
