package com.umgsai.wx.backend.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shangyidong
 * 2020/10/23 17:18
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class JiraVoucherTest {

    @Resource(name = "commonExecutor")
    protected ThreadPoolExecutor commonExecutor;

    @Test
    public void matin() {
        String path = "C:\\jiraFiles";
        String voucherPath = "C:\\jiraFiles\\voucher\\";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null){
            System.err.println("listOfFiles is null");
            return;
        }

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".json")) {
//                String content = FileUtils.readFileToString(file);
                String content = readTxt(file.getAbsolutePath());
                JSONObject jsonObject = JSON.parseObject(content);

                JSONObject result = jsonObject.getJSONObject("result");
                JSONArray issues = result.getJSONArray("issues");


                List<List<Object>> partition = Lists.partition(issues, 100);
                for (List<Object> objects : partition) {
                    List<CompletableFuture<Void>> completableFutureList = Lists.newArrayList();

                    for (Object issue : objects) {

                        completableFutureList.add(CompletableFuture.runAsync(() -> {
                            HttpRequest httpRequest = HttpUtil.createPost("https://uat-jira.xxx.com/kforderplatform/v1/getTaskProofs");

                            HashMap<String, String> headerMap = Maps.newHashMap();
                            headerMap.put("content-type", "application/json");
                            headerMap.put("Cookie", "xxx");
                            httpRequest.addHeaders(headerMap);

                            JSONObject issueJSONObject = (JSONObject) issue;
                            Integer taskId = issueJSONObject.getInteger("taskId");
                            Map<String, Object> bodyMap = Maps.newHashMap();
                            bodyMap.put("taskId", taskId);
                            httpRequest.body(JSON.toJSONString(bodyMap));
                            HttpResponse httpResponse = httpRequest.execute();

                            String body = httpResponse.body();

                            JSONObject voucherBody = JSON.parseObject(body);
                            JSONObject voucherBodyResult = voucherBody.getJSONObject("result");
                            if (voucherBodyResult == null) {
                                return;
                            }
                            JSONArray taskUserProofs = voucherBodyResult.getJSONArray("taskUserProofs");
                            JSONArray taskSuperProofs = voucherBodyResult.getJSONArray("taskSuperProofs");
                            Boolean isOrderTask = voucherBodyResult.getBoolean("isOrderTask");
                            if (taskUserProofs == null && taskSuperProofs == null) {
                                return;
                            }
                            System.out.println(Thread.currentThread().getName());
                            System.out.println(file.getAbsolutePath());
                            System.out.println(body);
                            writeTxt(voucherPath + taskId + ".json", body);
                        }, commonExecutor));
                    }
                    CompletableFuture<?>[] futureArray = new CompletableFuture[completableFutureList.size()];
                    CompletableFuture.allOf(completableFutureList.toArray(futureArray)).join();
                }
            }
        }
    }

    public static void writeTxt(String txtPath, String content) {
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if (file.exists()) {
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
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
