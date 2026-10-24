package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.ArticleDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.Resource;
import java.util.UUID;

@MapperScan("com.umgsai.wx.backend.dao")
@SpringBootTest
public class ArticleMapperTest {

    @Resource
    private ArticleMapper articleMapper;

    @Test
    public void testInsert() {
        ArticleDO articleDO = new ArticleDO();
        articleDO.setArticleId(UUID.randomUUID().toString());
        articleDO.setArticleContent("xxxx");
        articleDO.setAuthor("shang");
        int insert = articleMapper.insert(articleDO);
        Assertions.assertTrue(insert > 0);
    }
}
