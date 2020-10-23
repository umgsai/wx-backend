package com.umgsai.wx.backend.dao.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author shangyidong
 * 2020/10/23 19:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskData {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务所属工作组
     */
    private String taskGroup;

    /**
     * 任务来源
     */
    private String taskSource;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务优先级
     */
    private String taskPriority;

    /**
     * varchar(2048)
     */
    private String taskComment;

    /**
     * 用户id
     */
    private Long showNo;

    /**
     * 用户uid
     */
    private Long uid;

    /**
     * 任务处理人
     */
    private String taskAssignee;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 环信会话状态
     */
    private String ssid;

    /**
     * 进线手机号码
     */
    private String phoneNumber;

    /**
     * 是否投诉
     */
    private Integer isComplaint;

    /**
     * 用户凭证
     */
    private String userVouchers;

    /**
     * 大神凭证
     */
    private String godVouchers;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 申诉id
     */
    private String appealId;

    /**
     * 订单编号
     */
    private Long orderNo;

    /**
     * 任务日志
     */
    private String taskLogList;

    /**
     * 申诉数据json
     */
    private String complainInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
