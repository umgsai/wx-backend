package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.ArticleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {

    /**
     * [新增]
     *
     * @author Shang
     * @date 2020/05/05
     **/
    int insert(ArticleDO articleDO);

    /**
     * [刪除]
     *
     * @author Shang
     * @date 2020/05/05
     **/
    int deleteById(Long id);

    ArticleDO getById(Long id);

    ArticleDO getByArticleId(String articleId);

    /**
     * [更新]
     *
     * @author Shang
     * @date 2020/05/05
     **/
    int update(ArticleDO articleDO);

    List<ArticleDO> queryList(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int queryCount();

    int addVisits(String articleId);

    int addLikes(String articleId);
}
