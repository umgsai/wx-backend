package com.umgsai.wx.backend.controller.request;

import lombok.Data;

@Data
public class AddCommentRequest {

    private String articleId;

    private String commentContent;

    private String author;

    /**
     * 作者头像url
     */
    private String authorAvatar;
}
