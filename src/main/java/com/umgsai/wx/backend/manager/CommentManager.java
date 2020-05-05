package com.umgsai.wx.backend.manager;

import com.google.common.collect.Lists;
import com.umgsai.wx.backend.dao.CommentMapper;
import com.umgsai.wx.backend.dao.data.CommentDO;
import com.umgsai.wx.backend.model.Comment;
import com.umgsai.wx.backend.model.PageResult;
import com.umgsai.wx.backend.util.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CommentManager {

    @Resource
    private CommentMapper commentMapper;

    public Comment queryById(Long id) {
        CommentDO commentDO = commentMapper.getById(id);
        return Converter.convert(commentDO, Comment.class);
    }

    public List<Comment> queryByArticleId(String articleId) {
        List<CommentDO> commentDOS = commentMapper.getByArticleId(articleId);
        return convert(commentDOS);
    }

    public void addComment(String articleId, String commentContent, String author, String authorAvatar) {
        CommentDO commentDO = new CommentDO();
        commentDO.setCommentId(UUID.randomUUID().toString());
        commentDO.setArticleId(articleId);
        commentDO.setAuthor(author);
        commentDO.setAuthorAvatar(authorAvatar);
        commentDO.setCommentContent(commentContent);

        int insert = commentMapper.insert(commentDO);
        log.info("新增评论 articleId={}, commentContent={}， 新增记录数={}", articleId, commentContent, insert);
    }

    public void addViews(String articleId){

    }

    public PageResult<Comment> queryList(Integer pageNum, Integer pageSize) {
        int offset = pageSize * (pageNum - 1);
        List<CommentDO> commentDOS = commentMapper.queryList(offset, pageSize);
        List<Comment> comments = convert(commentDOS);
        int count = commentMapper.queryCount();

        return PageResult.newPageResult(comments, count, pageNum, pageSize);
    }

    private List<Comment> convert(List<CommentDO> commentDOS) {
        List<Comment> comments = Lists.newArrayList();
        for (CommentDO commentDO : commentDOS) {
            comments.add(Converter.convert(commentDO, Comment.class));
        }
        return comments;
    }
}
