package com.umgsai.wx.backend.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

//@RequestMapping("/")
@Slf4j
@Controller
public class PageController {

//    @RequestMapping("/")
//    public String index() {
//        return "redirect:/index.html";
//    }

    @RequestMapping("/index.html")
    public String indexHtml() {
        return "index";
    }

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户：" + name);

        return "index.html";
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object viewAdmin() {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("msg", "有ROLE_ADMIN权限");
        return resultMap;
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public Object viewUser() {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("msg", "有ROLE_ADMIN权限");
        return resultMap;
    }
}
