package com.bookmarket.service.impl;

import com.bookmarket.dto.response.Result;
import com.bookmarket.mapper.BookMapper;
import com.bookmarket.mapper.OrderMapper;
import com.bookmarket.pojo.Order;
import com.bookmarket.service.OrderService;
import com.bookmarket.utils.RedisUtil;
import com.bookmarket.utils.UserContext;
import com.bookmarket.vo.BookVO;
import com.bookmarket.vo.PageVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
* @author wetuh
* @description 针对表【order(订单表)】的数据库操作Service实现
* @createDate 2026-02-04 19:13:32
*/
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderMapper orderMapper;
    private final RedisUtil redisUtil;
    private final BookMapper bookMapper;



    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, RedisUtil redisUtil, BookMapper bookMapper) {
        this.orderMapper = orderMapper;
        this.redisUtil = redisUtil;
        this.bookMapper = bookMapper;
    }

    @Override
    public PageVO getMyOrders(Integer page, Integer pageSize, Long userId, String status) {
        PageHelper.startPage(page,pageSize);
        List<Order> ordersByUserId = orderMapper.getOrdersByUserId(userId, status);
        Page<Order> pageOrders = (Page<Order>) ordersByUserId;
        return new PageVO(pageOrders.getResult(),pageOrders.getTotal(),pageOrders.getPageNum(),pageOrders.getPageSize());
    }

    @Transactional
    @Override
    public Order generateOrder(Order order) {
        //订单号生成
        StringBuffer ordNo = new StringBuffer("ORD");
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = "ORD:" + now;
        Long increase = redisUtil.increase(key , 1L);
        String seq = String.format("%04d",increase);
        ordNo.append(now);
        ordNo.append(seq);
        //设置订单信息
        order.setOrderNo(ordNo.toString());
        BigDecimal quantity = BigDecimal.valueOf(order.getQuantity());
        order.setBuyerId(UserContext.getCurrentUserId());
        BookVO bookData = bookMapper.getBookById(order.getBookId());
        order.setBookId(bookData.getId());
        order.setBookCover(bookData.getCover());
        order.setBookTitle(bookData.getTitle());
        order.setPrice(bookData.getPrice());
        order.setTotalPrice(order.getPrice().multiply(quantity));
        orderMapper.save(order);
        order.setStatus("pending");
        order.setPaymentStatus("unpaid");
        order.setShippingStatus("unshipped");
        return order;
    }

    @Transactional
    @Override
    public Order confirm(Long orderId) {
        Boolean updated = orderMapper.updateOrder(orderId);
        System.out.println(LocalDateTime.now());
        if(updated){
            return orderMapper.getOrderById(orderId);
        }
        throw new RuntimeException("确认收获失败：重复确认");
    }

    @Override
    public Result cancel(Long orderId) {
        Boolean updated = orderMapper.cancelOrder(orderId);
        if(updated){
            Order orderById = orderMapper.getOrderById(orderId);
            return Result.success(orderById);
        }
        return Result.error("取消订单失败");
    }


}
