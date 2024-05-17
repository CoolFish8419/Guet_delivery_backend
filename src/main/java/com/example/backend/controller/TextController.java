package com.example.backend.controller;

import com.example.backend.dto.TextDTO;
import com.example.backend.dto.TextResponse;
import com.example.backend.service.TextService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/text")
public class TextController {
    @Resource
    private TextService textService;

    @PostMapping("/details")
    @ResponseBody
    public TextResponse Details(@RequestBody TextDTO textDTO){
        return textService.Details(textDTO);
    }
}
