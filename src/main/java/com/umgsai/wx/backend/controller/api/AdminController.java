package com.umgsai.wx.backend.controller.api;

import com.google.common.collect.Maps;
import com.umgsai.wx.backend.manager.AuthManager;
import com.umgsai.wx.backend.model.Response;
import com.umgsai.wx.backend.model.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequestMapping("/api")
@RestController
public class AdminController {

    @Resource
    private AuthManager authManager;

    @RequestMapping("/admin/login")
    public Object adminLogin(@RequestBody LoginRequest request) {
        Map<String, String> map = Maps.newHashMap();

        map.put("adminToken", UUID.randomUUID().toString());

        return Response.successResult(map);
    }

//    @RequestMapping("/login")
//    public Object login(@RequestBody LoginRequest request) {
//        Map<String, String> map = Maps.newHashMap();
//
//        map.put("adminToken", UUID.randomUUID().toString());
//
//        return Response.successResult(map);
//    }


}
