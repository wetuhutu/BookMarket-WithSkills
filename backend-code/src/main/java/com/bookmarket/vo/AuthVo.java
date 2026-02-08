package com.bookmarket.vo;

import com.bookmarket.pojo.Seller;

public class AuthVo {
    private String token;
    private Seller user;

    public AuthVo(String token, Seller seller) {
        this.token = token;
        this.user = seller;
    }

    @Override
    public String toString() {
        return "AuthVo{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seller getUser() {
        return user;
    }

    public void setUser(Seller user) {
        this.user = user;
    }
}
