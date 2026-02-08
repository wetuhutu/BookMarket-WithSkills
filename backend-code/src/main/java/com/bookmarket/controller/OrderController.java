package com.bookmarket.controller;

import com.bookmarket.dto.response.Result;
import com.bookmarket.pojo.Order;
import com.bookmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Result post(@RequestBody Order orderReq) {
        com.bookmarket.pojo.Order order = orderService.generateOrder(orderReq);
        return Result.success(order);
    }

    @PutMapping("/{orderId}/confirm")
    public Result confirmOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderService.confirm(orderId);
        return Result.success(order);
    }

    @PutMapping("/{orderId}/cancel")
    public Result cancelOrder(@PathVariable("orderId") Long orderId) {
        return orderService.cancel(orderId);
    }

}
