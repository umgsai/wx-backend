package com.umgsai.wx.backend.dao.data;

import lombok.Data;

import java.util.Date;

@Data
public class UserDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码密文
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
