package com.umgsai.wx.backend.model.request;

import lombok.Data;

@Data
public class PostArticleRequest  {

    private String title;

    private String articleContent;

    private String author;
}
