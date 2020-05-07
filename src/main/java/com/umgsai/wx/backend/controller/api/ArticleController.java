package com.umgsai.wx.backend.controller.api;

import com.umgsai.wx.backend.model.request.QueryArticleListRequest;
import com.umgsai.wx.backend.manager.ArticleManager;
import com.umgsai.wx.backend.model.Article;
import com.umgsai.wx.backend.model.PageResult;
import com.umgsai.wx.backend.model.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@RequestMapping("/api")
@RestController
public class ArticleController {

    @Resource(name = "commonExecutor")
    protected ThreadPoolExecutor commonExecutor;
    @Resource
    private ArticleManager articleManager;

    @RequestMapping("/article/view")
    public Object viewArticle(@RequestParam String articleId) {
        Article article = articleManager.queryByArticleId(articleId);
        commonExecutor.execute(() -> {
            articleManager.addVisits(articleId);
        });
        return Response.successResult(article);
    }

    @PostMapping("/article/addLikes")
    public Object addLikes(@RequestParam String articleId) {
        articleManager.addLikes(articleId);
        return Response.successResult(articleManager.queryByArticleId(articleId));
    }

    @RequestMapping("/article/list")
    public Object queryList(@RequestBody QueryArticleListRequest request) {
        PageResult<Article> articlePageResult = articleManager.queryList(request.getPageNum(), request.getPageSize());
        return Response.successResult(articlePageResult);
    }
}
