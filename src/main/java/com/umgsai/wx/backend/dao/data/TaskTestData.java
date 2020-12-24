package com.umgsai.wx.backend.dao.data;

import lombok.Data;

import java.util.Date;

/**
 * @author shangyidong
 * 2020/12/24 11:20
 */
@Data
public class TaskTestData {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务来源
     */
    private String taskSource;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 紧急程度，0=非紧急 1=紧急
     */
    private boolean urgentLevel;

    /**
     * 进线手机号码
     */
    private String phoneNumber;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户uid
     */
    private Long uid;

    /**
     * 任务创建人
     */
    private String taskCreator;

    /**
     * 任务处理人
     */
    private String taskAssignee;

    /**
     * 任务接收组
     */
    private String taskGroup;

    /**
     * 任务提示
     */
    private String taskHint;

    /**
     * 备注
     */
    private String taskRemark;

    /**
     * 任务状态 0=初始，待分配 1=处理中 2=预约中 3=已完成
     */
    private boolean taskStatus;

    /**
     * 额外信息，json格式
     */
    private String extraInfo;

    /**
     * 分配给当前处理人的时间
     */
    private Date assignTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * cost_time
     */
    private Integer costTime;

    /**
     * timeout
     */
    private Integer timeout;

    /**
     * reply_deadline
     */
    private Date replyDeadline;
}
