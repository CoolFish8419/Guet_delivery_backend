package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.dto.WalletDTO;
import com.example.backend.dto.WalletResponse;
import com.example.backend.entity.Wallet;
import com.example.backend.mapper.WalletMapper;
import com.example.backend.service.WalletService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {
    @Resource
    private WalletMapper walletMapper;

    @Override
    public WalletResponse Ma_All() {
        WalletResponse response = new WalletResponse();
        List<Wallet> wallets = walletMapper.selectList(new QueryWrapper<>());
        response.setData(wallets);
        response.setMessage("获取成功");
        return response;
    }

    @Override
    public WalletResponse newWallet(WalletDTO walletDTO) {
        Wallet wallet =new Wallet();
        BeanUtils.copyProperties(walletDTO, wallet);
        save(wallet);
        WalletResponse response = new WalletResponse();
        List<Wallet> responseList=new ArrayList<>();
        responseList.add(wallet);
        response.setMessage("用户钱包创建完成");
        response.setData(responseList);
        return response;
    }
    @Override
    public WalletResponse update(WalletDTO walletDTO) {
        UpdateWrapper<Wallet> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Wallet::getUserId, walletDTO.getUserId());
        Wallet wallet = walletMapper.selectOne(updateWrapper);
        if (wallet == null) {
            // 抛出异常或返回错误信息
            throw new RuntimeException("未找到对应的钱包");
        }
        if(walletDTO.getBalance() != null){
            wallet.setBalance(wallet.getBalance().add(walletDTO.getBalance()));
        }
        walletMapper.updateById(wallet);
        WalletResponse response = new WalletResponse();
        response.setMessage("更新成功");
        return response;
    }

    @Override
    public WalletResponse payBill(Integer user_id, Integer pay_key , BigDecimal balance) {
        WalletResponse response = new WalletResponse();
        UpdateWrapper<Wallet> updateWrapper = new UpdateWrapper<>();
        UpdateWrapper<Wallet> updateMainWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Wallet::getUserId,user_id);
        updateMainWrapper.lambda().eq(Wallet::getUserId,0);
        Wallet wallet = walletMapper.selectOne(updateWrapper);
        Wallet MainWallet = walletMapper.selectOne(updateMainWrapper);
        if (wallet != null) {
            if(pay_key==null){
                wallet.setBalance(wallet.getBalance().subtract(balance));
                MainWallet.setBalance(MainWallet.getBalance().add(balance));
                walletMapper.updateById(wallet);
                walletMapper.updateById(MainWallet);
                response.setMessage("订单完成");
                return response;
            }
            else if (wallet.getPayKey().equals(pay_key)) {
                if (wallet.getBalance().compareTo(balance) >= 0) {
                    wallet.setBalance(wallet.getBalance().subtract(balance));
                    MainWallet.setBalance(MainWallet.getBalance().add(balance));
                    walletMapper.updateById(wallet);
                    walletMapper.updateById(MainWallet);
                    System.out.println(wallet);
                    System.out.println(MainWallet);
                    response.setMessage("支付成功");
                } else {
                    response.setMessage("余额不足");
                }
            } else {
                response.setMessage("密码错误");
            }
        } else {
            response.setMessage("用户不存在");
        }
        return response;
    }


    @Override
    public List<Wallet> getBalance(Integer user_id) {
        List<Wallet> walletList =new ArrayList<>();
        QueryWrapper<Wallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Wallet::getUserId,user_id);
        walletList = walletMapper.selectList(queryWrapper);
        return walletList;
    }


    @Override
    public WalletResponse createOrder(Integer user_id , Integer payKey ,BigDecimal balance ) {
        WalletResponse response = new WalletResponse();
        QueryWrapper<Wallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Wallet::getUserId, user_id);
        Wallet wallet = walletMapper.selectOne(queryWrapper);
        if (wallet != null) {
            if (wallet.getPayKey().equals(payKey)) {
                if (wallet.getBalance().compareTo(balance) >= 0) {
                    wallet.setBalance(wallet.getBalance().subtract(balance));
                    walletMapper.updateById(wallet);
                    System.out.println(wallet);
                    response.setMessage("支付成功");
                } else {
                    response.setMessage("余额不足");
                }
            } else {
                response.setMessage("密码错误");
            }
        } else {
            response.setMessage("用户不存在");
        }
        return response;
    }

}
