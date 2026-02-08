package com.bookmarket.service;

import com.bookmarket.dto.response.Result;
import com.bookmarket.pojo.Order;
import com.bookmarket.vo.PageVO;

/**
* @author wetuh
* @description 针对表【order(订单表)】的数据库操作Service
* @createDate 2026-02-04 19:13:32
*/
public interface OrderService  {

    PageVO getMyOrders(Integer page, Integer pageSize, Long userId, String status);

    Order generateOrder(Order order);

    Order confirm(Long orderId);

    Result cancel(Long orderId);
}
