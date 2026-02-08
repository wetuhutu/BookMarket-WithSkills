package com.bookmarket.dto.response;

public class Result {
    private int code;
    private String message;
    private Object data;

    private Result() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(200, "success", null);
    }

    public static Result success(String message) {
        return new Result(200, message, null);
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result success(String message, Object data) {
        return new Result(200, message, data);
    }

    public static Result error() {
        return new Result(0, "error", null);
    }
    public static Result error(String message) {
        return new Result(0, message, null);
    }
    
    public static Result error(String message, Object data) {
        return new Result(0, message, data);
    }

}
