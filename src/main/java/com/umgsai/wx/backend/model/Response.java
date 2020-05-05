package com.umgsai.wx.backend.model;

import lombok.Data;

@Data
public class Response<T> {

    private T data;

    private boolean success;

    private String code;

    private String msg;

    public static <T> Response<T> successResult(T data) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> failResult(String code, String msg) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMsg(msg);
        return response;
    }
}
