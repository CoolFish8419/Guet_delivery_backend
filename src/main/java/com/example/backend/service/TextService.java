package com.example.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.dto.TextDTO;
import com.example.backend.dto.TextResponse;
import com.example.backend.entity.Text;

public interface TextService extends IService<Text> {
    TextResponse Details(TextDTO textDTO);

}
