package com.umgsai.wx.backend.dao.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkGroupDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 工作组最大分配任务数量
     */
    private Integer maxTaskCount;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 部门
     */
    private String department;

    /**
     * 工作时间配置
     */
    private String workTimeConfig;

    /**
     * 任务分配方式 0=默认预闲分配 1=领取分配 2=轮询分配
     */
    private Integer taskAssignType;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}