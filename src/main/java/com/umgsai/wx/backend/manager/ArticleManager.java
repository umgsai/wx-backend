package com.umgsai.wx.backend.manager;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.umgsai.wx.backend.dao.ArticleMapper;
import com.umgsai.wx.backend.dao.data.ArticleDO;
import com.umgsai.wx.backend.model.Article;
import com.umgsai.wx.backend.model.PageResult;
import com.umgsai.wx.backend.model.request.PostArticleRequest;
import com.umgsai.wx.backend.util.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class ArticleManager {
    @Resource
    private ArticleMapper articleMapper;

    public void postArticle(PostArticleRequest request) {
        ArticleDO articleDO = new ArticleDO();
        articleDO.setArticleId(UUID.randomUUID().toString());
        articleDO.setTitle(request.getTitle());
        articleDO.setAuthor(request.getAuthor());
        articleDO.setArticleContent(request.getArticleContent());
        int insert = articleMapper.insert(articleDO);

        log.info("新增文章 request={}, 新增记录数{}", JSON.toJSONString(request), insert);
    }

    public Article queryById(Long id) {
        ArticleDO articleDO = articleMapper.getById(id);
        return Converter.convert(articleDO, Article.class);
    }

    public List<Article> queryArticleList(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<ArticleDO> articleDOS = articleMapper.queryList(offset, pageSize);
        List<Article> articleList = Lists.newArrayList();
        for (ArticleDO articleDO : articleDOS) {
            articleList.add(Converter.convert(articleDO, Article.class));
        }
        return articleList;
    }

    public Article queryByArticleId(String articleId) {
        ArticleDO articleDO = articleMapper.getByArticleId(articleId);
        return Converter.convert(articleDO, Article.class);
    }

    public void addVisits(String articleId) {
        articleMapper.addVisits(articleId);
    }

    public void addLikes(String articleId) {
        articleMapper.addLikes(articleId);
    }

    public PageResult<Article> queryList(Integer pageNum, Integer pageSize) {
        int offset = pageSize * (pageNum - 1);
        List<ArticleDO> articleDOS = articleMapper.queryList(offset, pageSize);
        List<Article> articleList = convert(articleDOS);
        int count = articleMapper.queryCount();

        return PageResult.newPageResult(articleList, count, pageNum, pageSize);
    }

    private List<Article> convert(List<ArticleDO> articleDOS) {
        List<Article> articleList = Lists.newArrayList();
        for (ArticleDO articleDO : articleDOS) {
            articleList.add(Converter.convert(articleDO, Article.class));
        }
        return articleList;
    }
}
