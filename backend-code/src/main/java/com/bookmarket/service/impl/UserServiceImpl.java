package com.bookmarket.service.impl;


import com.bookmarket.dto.request.LoginRequest;
import com.bookmarket.mapper.UserMapper;
import com.bookmarket.pojo.Seller;
import com.bookmarket.service.UserService;
import com.bookmarket.utils.JwtUtil;
import com.bookmarket.vo.AuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author wetuh
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2026-01-27 13:41:06
*/
@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Seller getOneSeller(Long id){
        return userMapper.getSellerById(id);
    }

    @Override
    public AuthVo login(LoginRequest loginRequest){
        String username = loginRequest.getAccount();
        String inputPassword = loginRequest.getPassword();
        Seller user =  userMapper.findUserByUsername(username);
        if(user!= null && user.getPassword().equals(inputPassword)){
            //生成token
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());
            user.setPassword("");
            return new AuthVo(token,user);
        }
        return null;
    }

    @Transactional
    @Override
    public AuthVo register(Seller seller){
        userMapper.save(seller);
        Seller saved = userMapper.findUserByUsername(seller.getUsername());
        if(saved != null){
            saved.setPassword("");
            return new AuthVo("",saved);
        }
        return null;
    }

    @Override
    public Seller getOneSeller(String username) {
        Seller userByUsername = userMapper.findUserByUsername(username);
        userByUsername.setPassword("");
        return userByUsername;
    }

    @Override
    public Seller updateSeller(Seller seller,String usernameContext) {
        System.out.println("==========="+seller.getEmail());
        userMapper.update(seller,usernameContext);
        Seller updatedSeller = userMapper.findUserByUsername(seller.getUsername());
        return updatedSeller;
    }

}




