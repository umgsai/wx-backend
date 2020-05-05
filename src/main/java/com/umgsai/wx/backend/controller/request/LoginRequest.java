package com.umgsai.wx.backend.controller.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
