package com.umgsai.wx.backend.model;

import lombok.Data;

@Data
public class WorkGroupConfig {

    /**
     * 格式 15:00:00
     */
    private String beginTime;

    /**
     * 格式 15:00:00
     */
    private String endTime;

    /**
     * 是否全年无休
     */
    private Boolean workTwentyFourSeven;
}
