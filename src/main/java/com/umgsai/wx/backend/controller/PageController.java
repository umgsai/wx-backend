package com.umgsai.wx.backend.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Controller
public class PageController {

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String indexHtml() {
        return "index";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("登陆用户：" + name);
        return "index";
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
        resultMap.put("msg", "有ROLE_USER权限");
        return resultMap;
    }

    @RequestMapping("/article/list.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView viewArticleList() {
        ModelAndView modelAndView = new ModelAndView("/article/articleList");
        modelAndView.addObject("url", "/article/list.html");
        return modelAndView;
    }

    @RequestMapping("/article/create.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView viewCreateArticle() {
        ModelAndView modelAndView = new ModelAndView("/article/createArticle");
        modelAndView.addObject("url", "/article/create.html");
        return modelAndView;
    }
}
