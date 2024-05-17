package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.dto.TextDTO;
import com.example.backend.dto.TextResponse;
import com.example.backend.entity.Text;
import com.example.backend.mapper.TextMapper;
import com.example.backend.service.TextService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text> implements TextService {
    @Resource
    private TextMapper textMapper;


    @Override
    public TextResponse Details(TextDTO textDTO) {
        TextResponse response = new TextResponse();
        QueryWrapper<Text> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Text::getId, textDTO.getId());
        List<Text> textList= textMapper.selectList(queryWrapper);
        if(!textList.isEmpty()){
            response.setData(textList);
            response.setMessage("富文本查询成功");
        }else{
            response.setMessage("请输入正确的富文本id");

        }
        return response;
    }
}
