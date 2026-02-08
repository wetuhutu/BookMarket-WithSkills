package com.bookmarket.service;


import com.bookmarket.dto.request.LoginRequest;
import com.bookmarket.dto.request.RegisterRequest;
import com.bookmarket.pojo.Seller;
import com.bookmarket.vo.AuthVo;

/**
* @author wetuh
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2026-01-27 13:41:06
*/
public interface UserService {

    Seller getOneSeller(Long id);

    AuthVo login(LoginRequest loginRequest);

    AuthVo register(Seller seller);

    Seller getOneSeller(String username);

    Seller updateSeller(Seller seller,String usernameContext);
}
