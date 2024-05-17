package com.example.backend.controller;

import com.example.backend.dto.WalletDTO;
import com.example.backend.dto.WalletResponse;
import com.example.backend.entity.Wallet;
import com.example.backend.service.WalletService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/wallet")
public class walletController {
    @Resource
    private WalletService walletService;


    @PostMapping("/update")
    @ResponseBody
    public WalletResponse Update(@RequestBody WalletDTO WalletDTO){
        return walletService.update(WalletDTO);
    }

    @GetMapping("/getBalance")
    @ResponseBody
    public List<Wallet> Get(Integer user_id){
        return walletService.getBalance(user_id);
    }

    @GetMapping("/createOrder")
    @ResponseBody
    public WalletResponse CreateOrder(Integer user_id , Integer pay_key, BigDecimal balance ){
        return walletService.createOrder(user_id , pay_key, balance);
    }

    @PostMapping("/add")
    @ResponseBody
    public WalletResponse Add(@RequestBody WalletDTO WalletDTO){
        return walletService.newWallet(WalletDTO);
    }

    @GetMapping("/payBill")
    @ResponseBody
    public WalletResponse payBill(Integer user_id , Integer pay_key, BigDecimal balance){
        return walletService.payBill( user_id ,  pay_key,  balance);
    }

    @GetMapping("/ma_all")
    @ResponseBody
    public WalletResponse Ma_All(){
        return walletService.Ma_All();
    }
}
