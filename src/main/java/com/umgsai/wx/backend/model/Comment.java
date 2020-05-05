package com.umgsai.wx.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    /**
     * 主键id
     */
    private Long id;

    /**
     * _id    varchar(64)
     */
    private String commentId;

    /**
     * 文章id，uuid
     */
    private String articleId;

    /**
     * _content text        not null comment 文章内容
     */
    private String commentContent;

    /**
     * 作者
     */
    private String author;

    /**
     * 作者头像url
     */
    private String authorAvatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
