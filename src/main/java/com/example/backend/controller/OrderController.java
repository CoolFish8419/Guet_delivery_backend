package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;
import com.example.backend.dto.OrderResponse;
import com.example.backend.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @GetMapping("/detail")
    @ResponseBody
    public OrderResponse Detail(Integer orderId){
        return orderService.Detail(orderId);
    }

    @PostMapping("/delete")
    @ResponseBody
    public OrderResponse Delete(@RequestBody OrderDTO orderDTO){
        return orderService.Delete(orderDTO);
    }

    @PostMapping("/add")
    @ResponseBody
    public OrderResponse Add(@RequestBody OrderDTO orderDTO){
        return orderService.Add(orderDTO);
    }

    @PostMapping("/all")
    @ResponseBody
    public OrderResponse All(@RequestBody OrderDTO orderDTO){
        return orderService.All(orderDTO);
    }

    @GetMapping("/need_token_list")
    @ResponseBody
    public OrderResponse Need_Token_list(){
        return orderService.Need_token_list();
    }

    @PostMapping("/update")
    @ResponseBody
    public OrderResponse Update(@RequestBody OrderDTO orderDTO){
        return orderService.Update(orderDTO);
    }

    @PostMapping("/list")
    @ResponseBody
    public OrderResponse List(@RequestBody OrderDTO orderDTO){
        return orderService.List(orderDTO);
    }
    @PostMapping("/getPointed")
    @ResponseBody
    public OrderResponse getPointedOrder(@RequestBody OrderDTO orderDTO){
        return orderService.getPointedOrder(orderDTO);
    }
}
