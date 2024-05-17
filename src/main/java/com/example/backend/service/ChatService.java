package com.example.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.dto.ChatDTO;
import com.example.backend.dto.ChatResponse;
import com.example.backend.entity.Chat;

public interface ChatService extends IService<Chat> {

    ChatResponse getAllToIdZero();
    ChatResponse getList(Integer userId);
    ChatResponse Search(ChatDTO chatDTO);

    ChatResponse Add(ChatDTO chatDTO);

}
