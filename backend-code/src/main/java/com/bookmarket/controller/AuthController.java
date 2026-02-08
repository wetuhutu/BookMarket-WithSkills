package com.bookmarket.controller;

import com.bookmarket.dto.request.LoginRequest;
import com.bookmarket.dto.response.Result;
import com.bookmarket.pojo.Seller;
import com.bookmarket.service.UserService;
import com.bookmarket.vo.AuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest){
        AuthVo loginVO = userService.login(loginRequest);
        return Result.success(loginVO);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Seller seller){
        AuthVo registerVO = userService.register(seller);
        return Result.success(registerVO);
    }


}
