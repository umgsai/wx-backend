package com.umgsai.wx.backend.dao;

import com.umgsai.wx.backend.dao.data.UserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insert(UserDO userDO);

    int deleteById(Long id);

    UserDO getById(Long id);

    UserDO getByUsername(String username);

    int update(UserDO userDO);

}
