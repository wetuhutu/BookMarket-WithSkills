package com.bookmarket.mapper;

import com.bookmarket.dto.request.RegisterRequest;
import com.bookmarket.pojo.Seller;
import com.bookmarket.pojo.User;
import com.bookmarket.vo.AuthVo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wetuh
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2026-01-27 13:41:06
* @Entity com.bookmarket.pojo.User
*/
@Mapper
public interface UserMapper {

    Seller getSellerById(Long id);

    Seller findUserByUsername(String username);

    void save(Seller seller);

    void update(Seller seller,String usernameContext);
}




