package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.TaskTestData;

import java.util.List;

/**
 * @author shangyidong
 * 2020/12/24 11:20
 */
public interface TaskTestMapper {

    List<TaskTestData> queryAll();

    int insert(TaskTestData taskTestData);

    int deleteById(Long id);

    int update(TaskTestData taskTestData);
}
