package com.umgsai.wx.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping("/")
@Controller
public class PageController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/index.html";
    }

    @RequestMapping("/index.html")
    public String indexHtml() {
        return "index";
    }
}
