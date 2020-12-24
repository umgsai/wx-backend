package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.WorkGroupDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkGroupMapper {

    int insert(WorkGroupDO workGroupDO);

    int deleteByGroupName(@Param("groupName") String groupName);

    int update(WorkGroupDO workGroupDO);

    int updateByGroupName(WorkGroupDO workGroupDO);

    int updateWorkTimeConfig(@Param("id") Long id, @Param("workTimeConfig") String workTimeConfig);

    WorkGroupDO queryByGroupName(@Param("groupName") String groupName);

    WorkGroupDO queryByGroupNameWithDeleted(@Param("groupName") String groupName);

    List<WorkGroupDO> queryAll();

    WorkGroupDO queryById(@Param("id") Long id);
}
