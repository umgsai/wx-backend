package com.umgsai.wx.backend.manager;

import com.umgsai.wx.backend.dao.RoleMapper;
import com.umgsai.wx.backend.dao.UserMapper;
import com.umgsai.wx.backend.dao.UserRoleConfigMapper;
import com.umgsai.wx.backend.dao.data.RoleDO;
import com.umgsai.wx.backend.dao.data.UserDO;
import com.umgsai.wx.backend.dao.data.UserRoleConfigDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Shang
 * 2020/5/8 20:40
 */
@Component
public class AuthManager {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleConfigMapper userRoleConfigMapper;

    public UserDO getById(Long id) {
        return userMapper.getById(id);
    }

    public UserDO selectByName(String username) {
        return userMapper.getByUsername(username);
    }

    public RoleDO getByRoleName(String roleName){
        return roleMapper.getByRoleName(roleName);
    }

    public List<UserRoleConfigDO> queryByUsername(String username) {
        return userRoleConfigMapper.queryByUsername(username);
    }
}
