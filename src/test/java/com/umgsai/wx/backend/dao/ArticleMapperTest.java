package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.ArticleDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.UUID;

@MapperScan("com.umgsai.wx.backend.dao")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
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
        Assert.assertTrue(insert > 0);
    }
}
