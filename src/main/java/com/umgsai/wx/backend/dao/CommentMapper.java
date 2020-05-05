package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.CommentDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    /**
     * [新增]
     *
     * @author Shang
     * @date 2020/05/05
     **/
    int insert(CommentDO commentDO);

    /**
     * [刪除]
     *
     * @author Shang
     * @date 2020/05/05
     **/
    int deleteById(Long id);

    CommentDO getById(Long id);

    List<CommentDO> getByArticleId(String articleId);

    /**
     * [更新]
     *
     * @author Shang
     * @date 2020/05/05
     **/
    int update(CommentDO commentDO);

    List<CommentDO> queryList(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int queryCount();

}
