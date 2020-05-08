package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.RoleDO;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {

    int insert(RoleDO roleDO);

    int deleteById(Long id);

    RoleDO getById(Long id);

    RoleDO getByRoleName(String roleName);

    int update(RoleDO roleDO);

}
