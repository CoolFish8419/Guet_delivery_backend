package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.dto.*;
import com.example.backend.entity.Receiver;
import com.example.backend.entity.User;
import com.example.backend.entity.Wallet;
import com.example.backend.mapper.ReceiverMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.mapper.WalletMapper;
import com.example.backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private ReceiverMapper receiverMapper;
    @Resource
    private WalletMapper walletMapper;
    @Override
    public Response Login(LoginDTO loginDTO) {
        Response response=new Response();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getOpenId, loginDTO.getOpen_id());

        List<User> userList=userMapper.selectList(queryWrapper);

        if(userList.isEmpty()){
            User newUser = new User();
            newUser.setOpenId(loginDTO.getOpen_id());
            userMapper.insert(newUser);
            System.out.println(newUser);
            List<User> userList2=userMapper.selectList(queryWrapper);
            User user1=userList2.get(0);
            Wallet NewWallet = new Wallet();
            NewWallet.setUserId(user1.getUserId());
            walletMapper.insert(NewWallet);
        }
        List<User> userList1=userMapper.selectList(queryWrapper);
        User user=userList1.get(0);
        if(user.getOpenId().equals(loginDTO.getOpen_id())){
            LoginResponse loginResponse = new LoginResponse();
            BeanUtils.copyProperties(user, loginResponse);
            response.setData(loginResponse);
            response.setStatusCode(200);
            response.setMessage("登录成功");
            return response;
        } else{
            response.setStatusCode(400);
            response.setMessage("登录失败");
            return response;
        }
    }
    @Override
    public Response List() {
        Response response=new Response();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda();
        List<User> userList = userMapper.selectList(queryWrapper);
        response.setData(userList);
        return response;
    }
    @Override
    public Response Get(UserDTO userDTO) {
        Response response=new Response();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserId,userDTO.getUserId());
        List<User> userList = userMapper.selectList(queryWrapper);
        response.setData(userList);
        return response;
    }
    @Override
    public Response Delete(UserDTO userDTO) {
        Response response=new Response();
        QueryWrapper<com.example.backend.entity.User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userDTO.getUserId());
        int result = userMapper.delete(wrapper);
        if(result>0){
            response.setMessage("用户删除成功");
        }else{
            response.setMessage("用户删除错误");
        }
        return response;
    }
    @Override
    public Response Update(UserDTO userDTO) {
        Response response=new Response();
        User user1 = User.of(userDTO);
        int i = this.baseMapper.updateById(user1);

        if(i == 1){
            User user2 = this.baseMapper.selectById(user1.getUserId());
            if(userDTO.getUserType()==0){
                Receiver newReceiver = new Receiver();
                newReceiver.setUserId(user1.getUserId());
                receiverMapper.insert(newReceiver);
            }
            response.setData(user2);
            response.setStatusCode(200);
            response.setMessage("修改成功");
        }
        else {
            response.setMessage("修改失败");
        }

        return response;
    }


}
