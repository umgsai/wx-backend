package com.umgsai.wx.backend.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.umgsai.wx.backend.dao.TaskMapper;
import com.umgsai.wx.backend.dao.data.TaskData;
import com.umgsai.wx.backend.dao.data.TaskLogData;
import com.umgsai.wx.backend.util.CommonDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shangyidong
 * 2020/10/23 20:00
 */
@Slf4j
@MapperScan("com.umgsai.wx.backend.dao")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskMapperTest {

    @Resource
    private TaskMapper taskMapper;
    @Resource(name = "commonExecutor")
    protected ThreadPoolExecutor commonExecutor;

    @Test
    public void test() {
        String path = "C:\\jiraFiles";
        String voucherPath = "C:\\jiraFiles\\voucher\\";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            System.err.println("listOfFiles is null");
            return;
        }

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                String content = readTxt(file.getAbsolutePath());
                JSONObject jsonObject = JSON.parseObject(content);

                JSONObject result = jsonObject.getJSONObject("result");
                JSONArray issues = result.getJSONArray("issues");


                List<List<Object>> partition = Lists.partition(issues, 100);
                for (List<Object> objects : partition) {
                    List<CompletableFuture<Void>> completableFutureList = Lists.newArrayList();

                    for (Object issue : objects) {

                        completableFutureList.add(CompletableFuture.runAsync(() -> {

                            try {

                                JSONObject issueJSONObject = (JSONObject) issue;
                                Integer taskId = issueJSONObject.getInteger("taskId");

                                TaskData taskData = new TaskData();
                                taskData.setTaskId(issueJSONObject.getLong("taskId"));
                                taskData.setTaskGroup(issueJSONObject.getString("receiveGroup"));

                                taskData.setTaskSource(issueJSONObject.getString("taskSource"));
                                taskData.setTaskType(issueJSONObject.getString("taskType"));
                                taskData.setTaskPriority(issueJSONObject.getString("priority"));
                                taskData.setTaskComment(issueJSONObject.getString("createComment"));

                                taskData.setShowNo(issueJSONObject.getLong("showNo"));
                                taskData.setUid(issueJSONObject.getLong("uid"));
                                taskData.setTaskAssignee(issueJSONObject.getString("userName"));
                                taskData.setTaskStatus(issueJSONObject.getString("taskStatus"));
                                taskData.setSsid(issueJSONObject.getString("ssid"));
                                taskData.setPhoneNumber(issueJSONObject.getString("callerPhoneNum"));
                                taskData.setIsComplaint(Boolean.TRUE.equals(issueJSONObject.getBoolean("isComplaint")) ? 1 : 0);


                                taskData.setOrderId(issueJSONObject.getString("orderId"));
                                taskData.setOrderNo(issueJSONObject.getLong("orderNo"));
                                taskData.setAppealId(issueJSONObject.getString("appealId"));

                                JSONArray taskLogArray = issueJSONObject.getJSONArray("comments");

                                List<TaskLogData> taskLogDataList = Lists.newArrayList();
                                if (taskLogArray != null) {
                                    for (Object o : taskLogArray) {
                                        JSONObject taskLogJSONObject = (JSONObject) o;
                                        TaskLogData taskLogData = TaskLogData.builder().id(taskLogJSONObject.getLong("commentId")).
                                                operator(taskLogJSONObject.getString("commentUser")).
                                                createTime(taskLogJSONObject.getString("commentTime")).
                                                operationType(taskLogJSONObject.getString("commentContent")).
                                                remark(taskLogJSONObject.getString("comments")).
                                                build();
                                        taskLogDataList.add(taskLogData);
                                    }
                                }
                                taskData.setTaskLogList(JSON.toJSONString(taskLogDataList));

                                taskData.setComplainInfo("{}");

                                taskData.setCreateTime(CommonDateUtil.parseDate(issueJSONObject.getString("createTime"), CommonDateUtil.STRIKE_DATE_TIME));
                                taskData.setUpdateTime(CommonDateUtil.parseDate(issueJSONObject.getString("updateTime"), CommonDateUtil.STRIKE_DATE_TIME));

                                String taskUserProofs = issueJSONObject.getString("taskUserProofs");
                                String taskSuperProofs = issueJSONObject.getString("taskSuperProofs");

                                Set<String> userVouchers = Sets.newHashSet();
                                Set<String> godVouchers = Sets.newHashSet();

                                if (StringUtils.isNotBlank(taskUserProofs)) {
                                    userVouchers.addAll(JSON.parseArray(taskUserProofs, String.class));
                                }

                                if (StringUtils.isNotBlank(taskSuperProofs)) {
                                    godVouchers.addAll(JSON.parseArray(taskSuperProofs, String.class));
                                }

                                String voucherFilePath = voucherPath + taskId + ".json";
                                String voucherInfo = readTxt(voucherFilePath);
                                if (StringUtils.isNotBlank(voucherInfo)) {
                                    JSONObject voucherInfoObject = JSON.parseObject(voucherInfo);
                                    JSONObject voucherInfoObjectResult = voucherInfoObject.getJSONObject("result");
                                    JSONArray taskUserProofsArray = voucherInfoObjectResult.getJSONArray("taskUserProofs");
                                    if (taskUserProofsArray != null) {
                                        for (Object o : taskUserProofsArray) {
                                            JSONObject taskUserProofJSONObject = (JSONObject) o;
                                            String fileUrl = taskUserProofJSONObject.getString("fileUrl");
                                            userVouchers.add(fileUrl);
                                        }
                                    }
                                    JSONArray taskSuperProofsArray = voucherInfoObjectResult.getJSONArray("taskSuperProofs");
                                    if (taskSuperProofsArray != null) {
                                        for (Object o : taskSuperProofsArray) {
                                            JSONObject taskSuperProofJSONObject = (JSONObject) o;
                                            String fileUrl = taskSuperProofJSONObject.getString("fileUrl");
                                            godVouchers.add(fileUrl);
                                        }
                                    }
                                }

                                taskData.setUserVouchers(JSON.toJSONString(userVouchers));
                                taskData.setGodVouchers(JSON.toJSONString(godVouchers));

                                int insert = taskMapper.insert(taskData);

                            } catch (Exception e) {
                                log.error("", e);
                            }

                        }, commonExecutor));
                    }
                    CompletableFuture<?>[] futureArray = new CompletableFuture[completableFutureList.size()];
                    CompletableFuture.allOf(completableFutureList.toArray(futureArray)).join();
                }
            }
        }

        try {
            TimeUnit.MINUTES.sleep(5);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static String readTxt(String txtPath) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text);
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
