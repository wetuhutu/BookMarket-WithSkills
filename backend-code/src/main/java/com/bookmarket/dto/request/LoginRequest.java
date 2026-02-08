package com.bookmarket.dto.request;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求参数实体
 */

public class LoginRequest {
    /**
     * 账号（手机号/邮箱/用户名）
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码/验证码
     */
    @NotBlank(message = "密码/验证码不能为空")
    private String password;

    /**
     * 登录类型（password/sms，默认password）
     */
    private String type = "password";

    @Override
    public String toString() {
        return "LoginRequest{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
