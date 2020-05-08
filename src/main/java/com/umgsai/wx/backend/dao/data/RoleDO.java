package com.umgsai.wx.backend.dao.data;

import lombok.Data;

import java.util.Date;

@Data
public class RoleDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
