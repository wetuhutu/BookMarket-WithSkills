package com.bookmarket.exception;

import com.bookmarket.dto.response.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理唯一键冲突异常
    @ExceptionHandler(value = DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        // 获取底层的SQL异常
        Throwable cause = e.getCause();
        String errMsg = cause.getMessage();
        // 解析错误信息，判断是哪个字段重复
        if (errMsg.contains("user.phone")) {
            return Result.error("该手机号已被注册");
        } else if (errMsg.contains("user.username")) {
            return Result.error("该用户名已被占用");
        } else {
            return Result.error("数据已存在，无法重复添加");
        }
    }

    @ExceptionHandler(value = javax.validation.ConstraintViolationException.class)
    public Result handleValidationException(javax.validation.ConstraintViolationException e) {
        // 提取参数校验的错误提示
        String errMsg = e.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("参数格式错误");
        return Result.error(errMsg);
    }

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        System.out.println(e.getMessage());
        String errMsg = e.getMessage();
        if (errMsg.contains("Duplicate entry")){
            return Result.error("Duplicate");
        }
        return Result.error(e.getMessage());
    }
}
