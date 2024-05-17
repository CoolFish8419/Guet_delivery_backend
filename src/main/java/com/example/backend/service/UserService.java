package com.example.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.dto.LoginDTO;
import com.example.backend.dto.OrderResponse;
import com.example.backend.dto.Response;
import com.example.backend.dto.UserDTO;
import com.example.backend.entity.User;

public interface UserService extends IService<User> {

    Response Login(LoginDTO loginDTO);

    Response List();

    Response Get(UserDTO userDTO);

    Response Delete(UserDTO userDTO);

    Response Update(UserDTO userDTO);
}
