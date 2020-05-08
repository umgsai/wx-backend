package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.UserRoleConfigDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleConfigMapper {

    int insert(UserRoleConfigDO userRoleConfigDO);

    int deleteById(Long id);

    UserRoleConfigDO getById(Long id);

    List<UserRoleConfigDO> queryByUsername(String username);

    int update(UserRoleConfigDO userRoleConfigDO);

}
