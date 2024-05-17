package com.example.backend.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.dto.ChatDTO;
import com.example.backend.dto.ChatResponse;
import com.example.backend.entity.Chat;
import com.example.backend.mapper.ChatMapper;
import com.example.backend.service.ChatService;
import io.swagger.models.auth.In;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, com.example.backend.entity.Chat> implements ChatService {
    @Resource
    private ChatMapper chat;

    @Override
    public ChatResponse getAllToIdZero() {
        ChatResponse response = new ChatResponse();
        QueryWrapper<com.example.backend.entity.Chat> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(com.example.backend.entity.Chat::getToId, 0);

        List<com.example.backend.entity.Chat> chatList = chat.selectList(queryWrapper);
        response.setData(chatList);
        response.setMessage("获取成功");
        return response;
    }

    public ChatResponse getList(Integer userId) {
        ChatResponse response = new ChatResponse();
        QueryWrapper<com.example.backend.entity.Chat> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(com.example.backend.entity.Chat::getToId, userId)
                .or()
                .eq(com.example.backend.entity.Chat::getFromId, userId);
        List<com.example.backend.entity.Chat> chatList = chat.selectList(queryWrapper);
        Map<Integer, List<com.example.backend.entity.Chat>> chatMap = chatList.stream()
                .collect(Collectors.groupingBy(com.example.backend.entity.Chat::getOrderId));
        List<com.example.backend.entity.Chat> lastChatList = new ArrayList<>();
        for (Map.Entry<Integer, List<com.example.backend.entity.Chat>> entry : chatMap.entrySet()) {
            List<com.example.backend.entity.Chat> chats = entry.getValue();
            com.example.backend.entity.Chat lastChat = chats.stream()
                    .sorted(Comparator.comparing(com.example.backend.entity.Chat::getCreateTime)) // 请将getTimestamp替换为您的时间戳获取方法
                    .reduce((first, second) -> second)
                    .orElse(null);
            lastChatList.add(lastChat);
        }
        response.setData(lastChatList);
        response.setMessage("获取成功");
        return response;
    }


    @Override
    public ChatResponse Search(ChatDTO chatDTO) {
        ChatResponse response=new ChatResponse();
        QueryWrapper<com.example.backend.entity.Chat> queryWrapper = new QueryWrapper<>();
        if (chatDTO.getToId() != 0 && chatDTO.getFromId() != 0) {
            queryWrapper.lambda()
                    .eq(com.example.backend.entity.Chat::getOrderId, chatDTO.getOrderId())
                    .ne(com.example.backend.entity.Chat::getToId, 0);
        } else {
            queryWrapper.lambda()
                    .and(i -> i.eq(com.example.backend.entity.Chat::getToId, 0)
                            .or()
                            .eq(com.example.backend.entity.Chat::getFromId, 0));
        }

        List<com.example.backend.entity.Chat> chatList = chat.selectList(queryWrapper);
        response.setData(chatList);
        response.setMessage("搜索成功");
        return response;
    }

    @Override
    public ChatResponse Add(ChatDTO chatDTO) {
        ChatResponse response = new ChatResponse();
        com.example.backend.entity.Chat chat =new com.example.backend.entity.Chat();
        BeanUtils.copyProperties(chatDTO, chat);
        save(chat);
        response.setMessage("聊天记录添加成功");
        return response;
    }

}
