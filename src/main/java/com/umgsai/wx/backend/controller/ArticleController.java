package com.umgsai.wx.backend.controller;

import com.umgsai.wx.backend.manager.ArticleManager;
import com.umgsai.wx.backend.model.Article;
import com.umgsai.wx.backend.model.Response;
import com.umgsai.wx.backend.model.request.PostArticleRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ArticleController {

    @Resource
    private ArticleManager articleManager;

    @RequestMapping("/article/postArticle")
    public Object postArticle(HttpServletRequest httpServletRequest, @RequestBody PostArticleRequest request) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isBlank(request.getAuthor())) {
            request.setAuthor(name);
        }
        articleManager.postArticle(request);
        return Response.successResult(null);
    }

    @RequestMapping("/article/list")
    public Object queryArticleList(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 10 || pageSize > 20) {
            pageSize = 20;
        }
        List<Article> articleList = articleManager.queryArticleList(pageNum, pageSize);
        return Response.successResult(articleList);
    }
}
