package com.umgsai.wx.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> dataList;

    private Integer pageNum;

    private Integer pageSize;

    private Integer totalCount;

    private Integer totalPage;

    public Integer getTotalPage() {
        return (int) Math.ceil(Double.valueOf(totalCount) / Double.valueOf(pageSize));
    }

    public static <T> PageResult<T> newPageResult(List<T> dataList, Integer totalCount, Integer pageNum, Integer pageSize) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setDataList(dataList);
        pageResult.setTotalCount(totalCount);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        return pageResult;
    }
}
