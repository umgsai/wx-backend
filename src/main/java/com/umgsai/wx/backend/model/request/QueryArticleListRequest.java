package com.umgsai.wx.backend.model.request;

import lombok.Data;

@Data
public class QueryArticleListRequest {

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
