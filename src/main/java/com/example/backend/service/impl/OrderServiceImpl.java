package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.dto.OrderDTO;
import com.example.backend.dto.OrderResponse;
import com.example.backend.entity.*;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.mapper.ParcelMapper;
import com.example.backend.mapper.ReceiverMapper;
import com.example.backend.service.OrderService;
import com.example.backend.util.Tools;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, com.example.backend.entity.Order> implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource

    private ReceiverMapper receiverMapper;
    @Resource

    private ParcelMapper parcelMapper;

    @Override
    public OrderResponse All(OrderDTO orderDTO) {
        OrderResponse response = new OrderResponse();
        List<com.example.backend.entity.Order> orderList = orderMapper.selectList(new QueryWrapper<com.example.backend.entity.Order>()
                .eq("publisher_user_id", orderDTO.getOrderId()));
        response.setData(orderList);
        response.setMessage("获取成功");
        return response;
    }

    @Override
    public OrderResponse Need_token_list() {
        OrderResponse response = new OrderResponse();
        List<com.example.backend.entity.Order> orderList = orderMapper.selectList(new QueryWrapper<com.example.backend.entity.Order>()
                .eq("status", 2));
        response.setData(orderList);
        response.setMessage("获取成功");
        return response;

    }

    public Map<String, List<Double>> getSortedDistances() {
        List<Receiver> receivers = receiverMapper.selectList(new QueryWrapper<Receiver>().eq("status", 1));
        List<Parcel> parcels = parcelMapper.selectList(new QueryWrapper<>());

        // 创建一个映射，用于存储每个Parcel名称和对应的按距离排序的距离列表
        Map<String, List<Double>> parcelToSortedDistances = new HashMap<>();

        // 遍历每个Parcel
        for (Parcel parcel : parcels) {
            List<Double> distances = new ArrayList<>();

            // 计算每个Receiver到这个Parcel的距离，并添加到列表中
            for (Receiver receiver : receivers) {
                String[] receiverCoordinates = receiver.getCoordinates().split(",");
                double receiverLat = Double.parseDouble(receiverCoordinates[1]);
                double receiverLon = Double.parseDouble(receiverCoordinates[0]);
                double distance = Tools.calculateDistance(receiverLat, receiverLon, parcel.getLatitude(), parcel.getLongitude());
                distances.add(distance);
            }

            // 根据距离对列表进行排序
            Collections.sort(distances);

            // 将排序后的距离列表存储在映射中
            parcelToSortedDistances.put(parcel.getName(), distances);
        }

        return parcelToSortedDistances; // 返回映射
    }

    @Override
    public OrderResponse List(OrderDTO orderDTO) {
        OrderResponse response = new OrderResponse();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();

        if (orderDTO.getPublisherUserId() != null) {
            if (orderDTO.getStatus() == 0) {
                queryWrapper.lambda().eq(Order::getPublisherUserId, orderDTO.getPublisherUserId());
            } else {
                queryWrapper.lambda().eq(Order::getPublisherUserId, orderDTO.getPublisherUserId()).eq(Order::getStatus, orderDTO.getStatus());
            }
            return getResponse(queryWrapper);
        } else if (orderDTO.getReceiverUserId() != null) {
            if (orderDTO.getStatus() == 2) {
//                Tools.calculateScore();
                queryWrapper.lambda().eq(Order::getStatus, orderDTO.getStatus());
//                Tools.test();//这里编写众包算法
            } else if (orderDTO.getStatus() == 0) {
                queryWrapper.lambda().eq(Order::getReceiverUserId, orderDTO.getReceiverUserId());
            } else {
                queryWrapper.lambda().eq(Order::getReceiverUserId, orderDTO.getReceiverUserId()).eq(Order::getStatus, orderDTO.getStatus());
            }
            return getResponse(queryWrapper);
        } else if (orderDTO.getStatus() != null) {
            //管理员
            if (orderDTO.getStatus() == 0) {
                queryWrapper.lambda();
            } else {
                queryWrapper.lambda().eq(Order::getStatus, orderDTO.getStatus());
            }
            return getResponse(queryWrapper);
        }

        response.setMessage("无订单信息");
        return response;
    }

    @Override
    public OrderResponse getPointedOrder(OrderDTO orderDTO) {
        OrderResponse response = new OrderResponse();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (orderDTO.getReceiverUserId() != null) {
            if (orderDTO.getStatus() == 2) {
                queryWrapper.lambda().eq(Order::getStatus, orderDTO.getStatus()).eq(Order::getPointed, orderDTO.getReceiverUserId());
            }
            return getResponse(queryWrapper);
        }
        return response;
    }

    private OrderResponse getResponse(QueryWrapper<Order> queryWrapper) {
        OrderResponse response = new OrderResponse();
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        if (!orderList.isEmpty()) {
            response.setData(orderList);
            response.setMessage("获取成功");
        } else {
            response.setMessage("无订单信息");
        }
        return response;
    }

    @Override
    public OrderResponse Delete(OrderDTO orderDTO) {
        OrderResponse response = new OrderResponse();
        QueryWrapper<com.example.backend.entity.Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderDTO.getOrderId());
        int result = orderMapper.delete(wrapper);
        if (result > 0) {
            response.setMessage("订单删除成功");
        } else {
            response.setMessage("订单删除错误");
        }
        return response;
    }

    @Override
    public OrderResponse Add(OrderDTO orderDTO) {
        OrderResponse response = new OrderResponse();
        com.example.backend.entity.Order order = new com.example.backend.entity.Order();
        QueryWrapper<com.example.backend.entity.Receiver> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<Receiver> receiversList = receiverMapper.selectList(wrapper);
        BeanUtils.copyProperties(orderDTO, order);
        Optional<Receiver> optionalReceiver = Tools.assignCourier(receiversList, order);
        if (optionalReceiver.isPresent()) {
            Receiver receiver = optionalReceiver.get();
            order.setPointed(receiver.getUserId());
        }
        save(order);
        response.setMessage("订单添加成功");
        return response;
    }

    @Override
    public OrderResponse Detail(Integer orderId) {
        OrderResponse response = new OrderResponse();
        QueryWrapper<com.example.backend.entity.Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(com.example.backend.entity.Order::getOrderId, orderId);
        List<com.example.backend.entity.Order> orderList = orderMapper.selectList(queryWrapper);
        response.setData(orderList);
        response.setMessage("搜索成功");
        return response;

    }

    @Override
    public OrderResponse Update(OrderDTO orderDTO) {
        OrderResponse response = new OrderResponse();
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Order::getOrderId, orderDTO.getOrderId());
        // 根据传入的参数名修改相应的值
        if (orderDTO.getStatus() != null) {
            updateWrapper.lambda().set(Order::getStatus, orderDTO.getStatus());
        }
        if (orderDTO.getPublisherAddress() != null) {
            updateWrapper.lambda().set(Order::getPublisherAddress, orderDTO.getPublisherAddress());
        }
        if (orderDTO.getReceiverUserId() != null) {
            updateWrapper.lambda().set(Order::getReceiverUserId, orderDTO.getReceiverUserId());
            updateWrapper.lambda().set(Order::getReceiveTaskTime, LocalDateTime.now());
        }
        if (orderDTO.getGetPackage() != null) {
            updateWrapper.lambda().set(Order::getTakePackageTime, LocalDateTime.now());
        }
        if (orderDTO.getArrive() != null) {
            updateWrapper.lambda().set(Order::getArriveTime, LocalDateTime.now());
        }
        if (orderDTO.getAccidentMessage() != null) {
            updateWrapper.lambda().set(Order::getAccidentMessage, orderDTO.getAccidentMessage());
        }
        if (orderDTO.getRemark() != null) {
            updateWrapper.lambda().set(Order::getRemark, orderDTO.getRemark());
        }
        if (orderDTO.getAccidentMessage() != null) {
            updateWrapper.lambda().set(Order::getAccidentMessage, orderDTO.getAccidentMessage());
        }
        if (orderDTO.getPickupCode() != null) {
            updateWrapper.lambda().set(Order::getPickupCode, orderDTO.getPickupCode());
        }
        if (orderDTO.getScore() != null) {
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Order::getOrderId, orderDTO.getOrderId());
            Order order = orderMapper.selectOne(queryWrapper);
            Integer receiverUserId = order.getReceiverUserId();

            updateWrapper.lambda().set(Order::getScore, orderDTO.getScore());
            UpdateWrapper<Receiver> updateWrapper1 = new UpdateWrapper<>();
            QueryWrapper<Order> finishList = new QueryWrapper<>();
            finishList.lambda().eq(Order::getStatus, 4).eq(Order::getReceiverUserId, receiverUserId);
            List<Order> finishedOrders = orderMapper.selectList(finishList);
            List<Float> scores = finishedOrders.stream()
                    .map(Order::getScore) // 假设Order类有一个getScore方法
                    .collect(Collectors.toList());
            System.out.println(scores);
            // 将本次传入的评分添加到评分列表
            scores.add(orderDTO.getScore());
            int finishNum = scores.size();
            // 计算平均分
            float averageScore = (float) scores.stream()
                    .mapToDouble(Float::doubleValue)
                    .average()
                    .orElse(0.0);
            updateWrapper1.lambda().eq(Receiver::getUserId, receiverUserId);
            Receiver receiver = new Receiver();
            receiver.setScore(averageScore);
            receiver.setFinishOrderNumber(finishNum);
            receiverMapper.update(receiver, updateWrapper1);
        }
        if (orderDTO.getComment() != null) {
            updateWrapper.lambda().set(Order::getComment, orderDTO.getComment());
        }
        int rows = orderMapper.update(null, updateWrapper);
        if (rows > 0) {
            response.setMessage("修改成功");
        } else {
            response.setMessage("修改失败");
        }
        return response;

    }
}
