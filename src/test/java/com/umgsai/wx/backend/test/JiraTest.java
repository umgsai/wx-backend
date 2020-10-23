package com.umgsai.wx.backend.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author shangyidong
 * 2020/10/23 16:32
 */
//@MapperScan("com.umgsai.wx.backend.dao")
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
public class JiraTest {

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

    //    @Test
    public void test() {
        HttpRequest httpRequest = HttpUtil.createPost("https://uat-jira.xxx.com/kforderplatform/v2/getKFTaskListInfo");

        HashMap<String, String> headerMap = Maps.newHashMap();
        headerMap.put("content-type", "application/json");
        headerMap.put("Cookie", "xxx");
        httpRequest.addHeaders(headerMap);

        Map<String, Object> bodyMap = Maps.newHashMap();
        bodyMap.put("pageSize", 1000);

        int pageNum = 1;
        while (true) {
            String fileName = "C:\\jiraFiles\\jira-" + pageNum + ".json";
            bodyMap.put("pageNum", pageNum++);
            httpRequest.body(JSON.toJSONString(bodyMap));

            HttpResponse httpResponse = httpRequest.execute();

            String body = httpResponse.body();

            JSONObject jsonObject = JSON.parseObject(body);
            Integer code = jsonObject.getInteger("code");
            if (!Objects.equals(code, 8000)) {
                System.err.println(body);
                return;
            }
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray issues = result.getJSONArray("issues");
            if (issues == null || issues.size() <= 0) {
                return;
            }

            writeTxt(fileName, body);

//            System.out.println(body);
        }


    }

    public static void main(String[] args) {
        new JiraTest().test();
    }
}
