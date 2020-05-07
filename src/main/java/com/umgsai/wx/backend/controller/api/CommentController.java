package com.umgsai.wx.backend.controller.api;

import com.umgsai.wx.backend.model.request.AddCommentRequest;
import com.umgsai.wx.backend.model.request.QueryCommentListRequest;
import com.umgsai.wx.backend.manager.CommentManager;
import com.umgsai.wx.backend.model.Comment;
import com.umgsai.wx.backend.model.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api")
@RestController
public class CommentController {

    @Resource
    private CommentManager commentManager;

    @RequestMapping("/comment/list")
    public Object queryList(@RequestBody QueryCommentListRequest request) {
        List<Comment> comments = commentManager.queryByArticleId(request.getArticleId());
        return Response.successResult(comments);
    }

    @RequestMapping("/comment/add")
    public Object queryList(@RequestBody AddCommentRequest request) {
        commentManager.addComment(request.getArticleId(), request.getCommentContent(), request.getAuthor(), request.getAuthorAvatar());
        return Response.successResult(null);
    }
}
