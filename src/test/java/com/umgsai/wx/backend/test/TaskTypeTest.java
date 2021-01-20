package com.umgsai.wx.backend.test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
//@MapperScan("com.umgsai.wx.backend.dao")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskTypeTest {


    @Test
    public void test() {
        String template = "{\n" +
                "                    \"complainType\":\"xxxxxxxx\",\n" +
                "                    \"thirdComplainTypeList\":[\n" +
                "\n" +
                "                    ]\n" +
                "                }";

        String s = "无效对话\t,\n" +
                "商务合作\t,\n" +
                "反馈/吐槽/抱怨夜间不提供在线咨询\t,\n" +
                "基础功能\t,\n";
        s = StringUtils.trim(s);
        String[] split = StringUtils.split(s, ",");

        List<String> resultList = Lists.newArrayList();
        for (String s1 : split) {
//            System.out.println(StringUtils.trim(s1));
            resultList.add(StringUtils.replace(template, "xxxxxxxx", StringUtils.trim(s1)));
        }

        System.out.println(Joiner.on(",").join(resultList));
    }
}
