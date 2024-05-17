package com.example.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.dto.OrderDTO;
import com.example.backend.dto.OrderResponse;
import com.example.backend.entity.Order;

public interface OrderService extends IService<Order> {
    OrderResponse All(OrderDTO orderDTO);
    OrderResponse Need_token_list();
    OrderResponse List(OrderDTO orderDTO);

    OrderResponse getPointedOrder(OrderDTO orderDTO);

    OrderResponse Delete(OrderDTO orderDTO);
    OrderResponse Update(OrderDTO orderDTO);
    OrderResponse Add(OrderDTO orderDTO);
    OrderResponse Detail(Integer orderId );
}
