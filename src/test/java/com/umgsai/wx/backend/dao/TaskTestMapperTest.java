package com.umgsai.wx.backend.dao;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.umgsai.wx.backend.dao.data.TaskTestData;
import com.umgsai.wx.backend.dao.data.WorkGroupDO;
import com.umgsai.wx.backend.manager.HolidayManager;
import com.umgsai.wx.backend.model.WorkGroupConfig;
import com.umgsai.wx.backend.util.CommonDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shangyidong
 * 2020/12/24 11:23
 */
@Slf4j
@MapperScan("com.umgsai.wx.backend.dao")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskTestMapperTest {

    @Resource
    private TaskTestMapper taskTestMapper;
    @Resource
    private HolidayManager holidayManager;
    @Resource
    private WorkGroupMapper workGroupMapper;

    @Test
    public void test() throws Exception {
        Map<String, Integer> map = Maps.newHashMap();
        map.put("内容风控日常", 960);//内容风控
        map.put("资金风控日常", 120);//资金风控
        map.put("电竞运营", 120);//业务产品
        map.put("售后工单", 60);//客服中心
        map.put("在线客服", 60);//客服中心
        map.put("Bug反馈组", 60);//客服中心

        List<TaskTestData> taskTestDataList = taskTestMapper.queryAll();
        for (TaskTestData taskTestData : taskTestDataList) {
            String taskGroup = taskTestData.getTaskGroup();
            Integer replyMinutes = map.get(taskGroup);
            if (replyMinutes == null) {
                replyMinutes = 120;
            }
            WorkGroupDO workGroupDO = workGroupMapper.queryByGroupName(taskGroup);
            WorkGroupConfig workGroupConfig = JSON.parseObject(workGroupDO.getWorkTimeConfig(), WorkGroupConfig.class);
            Date date = queryReplyDeadline(taskTestData.getCreateTime(), replyMinutes, workGroupConfig.getWorkTwentyFourSeven(), workGroupConfig.getBeginTime(), workGroupConfig.getEndTime());

//            System.out.printf(CommonDateUtil.formatDate(date, CommonDateUtil.STRIKE_DATE_TIME));

            TaskTestData updateTaskTestData = new TaskTestData();
            updateTaskTestData.setId(taskTestData.getId());
            updateTaskTestData.setReplyDeadline(date);
            taskTestMapper.update(updateTaskTestData);
        }
    }

    private Date queryReplyDeadline(Date date, long replyMinutes, Boolean workTwentyFourSeven, String beginTime, String endTime) throws Exception {
        if (StringUtils.compare(beginTime, endTime) >= 0) {
            String errorMsg = String.format("工作起始时间应早于结束时间, beginTime=%s, endTime=%s", beginTime, endTime);
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        String dateStr = CommonDateUtil.formatDate(date, CommonDateUtil.YYYY_MM_DD);
        Date todayBeginTime = CommonDateUtil.parseDate(dateStr + " " + beginTime, CommonDateUtil.STRIKE_DATE_TIME);
        if (holidayManager.isHoliday(dateStr) && Boolean.FALSE.equals(workTwentyFourSeven)) {
            // 如果今天为非工作日，则计算方式与明早10点的计算方式相同
            return queryReplyDeadline(CommonDateUtil.addDays(todayBeginTime, 1), replyMinutes, workTwentyFourSeven, beginTime, endTime);
        }
        String timeStr = CommonDateUtil.formatDate(date, "HH:mm:ss");
        // 今天是工作日
        if (StringUtils.compare(timeStr, beginTime) < 0) {
            // 还没到工作时间
            return queryReplyDeadline(todayBeginTime, replyMinutes, workTwentyFourSeven, beginTime, endTime);
        }
        if (StringUtils.compare(timeStr, endTime) > 0) {
            // 如果今天已经下班，则计算方式与明早10点的计算方式相同
            return queryReplyDeadline(CommonDateUtil.addDays(todayBeginTime, 1), replyMinutes, workTwentyFourSeven, beginTime, endTime);
        }
        Date todayWorkOffTime = CommonDateUtil.parseDate(dateStr + " " + endTime, CommonDateUtil.STRIKE_DATE_TIME);
        long timeDiffMinutes = CommonDateUtil.getTimeDiffMinutes(date, todayWorkOffTime);
        if (timeDiffMinutes >= replyMinutes) {
            return CommonDateUtil.addMinutes(date, (int) replyMinutes);
        }
        return queryReplyDeadline(CommonDateUtil.addDays(todayBeginTime, 1), replyMinutes - timeDiffMinutes, workTwentyFourSeven, beginTime, endTime);
    }
}
