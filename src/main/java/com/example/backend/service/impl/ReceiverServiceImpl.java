package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.dto.ReceiverDTO;
import com.example.backend.dto.ReceiverResponse;
import com.example.backend.entity.Receiver;
import com.example.backend.mapper.ReceiverMapper;
import com.example.backend.service.ReceiverService;
import com.example.backend.util.Tools;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.List;

@Service
public class ReceiverServiceImpl extends ServiceImpl<ReceiverMapper, Receiver> implements ReceiverService {
    @Resource
    private ReceiverMapper receiverMapper;


    @Override
    public ReceiverResponse All(ReceiverDTO receiverDTO) {
        ReceiverResponse response=new ReceiverResponse();
        List<Receiver> receivers = receiverMapper.selectList(new QueryWrapper<>());
        response.setData(receivers);
        response.setMessage("获取成功");
        return response;

    }

    @Override
    public ReceiverResponse Ma_All() {
        ReceiverResponse response=new ReceiverResponse();
        List<Receiver> receivers = receiverMapper.selectList(null);
        response.setData(receivers);
        response.setMessage("获取成功");
        return response;
    }

    @Override
    public ReceiverResponse Search(ReceiverDTO receiverDTO) {
        ReceiverResponse response=new ReceiverResponse();
        List<Receiver> receivers = receiverMapper.selectList(new QueryWrapper<Receiver>()
                .lambda().like(Receiver::getUserId, receiverDTO.getUserId()));
        response.setData(receivers);
        response.setMessage("搜索成功");
        return response;
    }

    @Override
    public ReceiverResponse Delete(ReceiverDTO receiverDTO) {
        ReceiverResponse response=new ReceiverResponse();
        QueryWrapper<Receiver> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda().like(Receiver::getReceiverId, receiverDTO.getReceiverId());
        int result= receiverMapper.delete(queryWrapper);
        if(result>0){
            response.setMessage("餐馆删除成功");
        }else{
            response.setMessage("餐馆删除错误");
        }
        return response;
    }

    @Override
    public ReceiverResponse Update(ReceiverDTO receiverDTO) {
        ReceiverResponse response = new ReceiverResponse();
        // 创建 UpdateWrapper 对象
        UpdateWrapper<Receiver> updateWrapper = new UpdateWrapper<>();
        // 设置条件
        updateWrapper.eq("user_id", receiverDTO.getUserId());
        // 根据 DTO 中的非空属性更新数据
        if (receiverDTO.getStatus() != null) {
            updateWrapper.set("status", receiverDTO.getStatus());
        }
        if (receiverDTO.getCoordinates() != null) {
            updateWrapper.set("coordinates", receiverDTO.getCoordinates());
        }
        // ... 你可以为 DTO 中的每个属性添加类似的代码
        // 执行更新
        receiverMapper.update(null, updateWrapper);
        // 查询更新后的数据
        List<Receiver> receivers = receiverMapper.selectList(new QueryWrapper<Receiver>()
                .lambda().eq(Receiver::getUserId, receiverDTO.getUserId()));
        response.setData(receivers);
        response.setMessage("数据更新成功");
        return response;
    }







    @Override
    public ReceiverResponse Add(ReceiverDTO receiverDTO) {
        ReceiverResponse response=new ReceiverResponse();
        Receiver receiver =new Receiver();
        BeanUtils.copyProperties(receiverDTO, receiver);
        save(receiver);
        response.setMessage("餐馆修改成功");
        return response;
    }

    @Override
    public ReceiverResponse Details(ReceiverDTO receiverDTO) {
        ReceiverResponse response=new ReceiverResponse();
        QueryWrapper<Receiver> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Receiver::getUserId, receiverDTO.getUserId());
        List<Receiver> receivers = receiverMapper.selectList(queryWrapper);
        if(!receivers.isEmpty()){
            response.setData(receivers);
            response.setMessage("骑手查询成功");
        }
        else {
            response.setMessage("无该骑手信息，请联系管理员");
        }
        return response;
    }
}

