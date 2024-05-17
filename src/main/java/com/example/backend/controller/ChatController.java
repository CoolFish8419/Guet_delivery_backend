package com.example.backend.controller;

import com.example.backend.dto.ChatDTO;
import com.example.backend.dto.ChatResponse;
import com.example.backend.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private ChatService chatService;
    @PostMapping("/search")
    @ResponseBody
    public ChatResponse Search(@RequestBody ChatDTO chatDTO){
        return chatService.Search(chatDTO);
    }

    @PostMapping("/add")
    @ResponseBody
    public ChatResponse Add(@RequestBody ChatDTO chatDTO){
        return chatService.Add(chatDTO);
    }

    @GetMapping("/getAllFeedBack")
    @ResponseBody
    public ChatResponse getAllToIdZero(){
        return chatService.getAllToIdZero();
    }
    @GetMapping("/list")
    @ResponseBody
    public ChatResponse getList(Integer userId){
        return chatService.getList(userId);
    }

}
