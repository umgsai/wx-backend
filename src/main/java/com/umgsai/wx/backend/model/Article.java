package com.umgsai.wx.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章id，uuid
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章缩略图
     */
    private String thumbnail;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 作者
     */
    private String author;

    /**
     * 阅览数量
     */
    private Integer visits;

    /**
     * 点赞数量
     */
    private Integer likes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
