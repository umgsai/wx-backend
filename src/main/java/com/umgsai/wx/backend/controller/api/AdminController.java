package com.umgsai.wx.backend.controller.api;

import com.google.common.collect.Maps;
import com.umgsai.wx.backend.model.request.LoginRequest;
import com.umgsai.wx.backend.model.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/api")
@RestController
public class AdminController {

    @RequestMapping("/admin/login")
    public Object login(@RequestBody LoginRequest request) {
        Map<String, String> map = Maps.newHashMap();

        map.put("adminToken", UUID.randomUUID().toString());

        return Response.successResult(map);
    }
}
