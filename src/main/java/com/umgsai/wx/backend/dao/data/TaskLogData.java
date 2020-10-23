package com.umgsai.wx.backend.dao.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author shangyidong
 * 2020/10/23 20:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLogData {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 处理人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;


}
