package com.bookmarket.mapper;


import com.bookmarket.pojo.Book;
import com.bookmarket.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author wetuh
* @description 针对表【order(订单表)】的数据库操作Mapper
* @createDate 2026-02-04 19:13:32
* @Entity com.bookmarket.pojo.Order
*/
@Mapper
public interface OrderMapper {

    List<Order> getOrdersByUserId(Long userId,String status);

    Long save(Order order);

    Boolean updateOrder(Long orderId);

    Boolean cancelOrder(Long orderId);

    Order getOrderById(Long orderId);
}
