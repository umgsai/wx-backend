package com.umgsai.wx.backend.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class Converter {

    public static Map<String, Object> convertObjectToMap(Object object) throws Exception {
        Map<String, Object> resultMap = Maps.newHashMap();
        Class clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.substring(0, 3).equals("get")) {
                Object value = method.invoke(object);
                String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                resultMap.put(fieldName, value);
            }
        }
        return resultMap;
    }

    public static <T> T convert(Object o, Class<T> tClass) {
        if (o == null) {
            return null;
        }
        try {
            T t = tClass.newInstance();
            BeanUtils.copyProperties(o, t);
            return t;
        } catch (Exception e) {
            String errorMsg = String.format("ObjectConverter convert Exception, o=%s, tClass=%s, %s", JSON.toJSONString(o), tClass.getName(), e.getMessage());
            log.error(errorMsg, e);
        }
        return null;
    }

    public static <T> T convert(Object o, Class<T> tClass, String... ignoreProperties) {
        if (o == null) {
            return null;
        }
        try {
            T t = tClass.newInstance();
            BeanUtils.copyProperties(o, t, ignoreProperties);
            return t;
        } catch (Exception e) {
            String errorMsg = String.format("ObjectConverter convert Exception, o=%s, tClass=%s, ignoreProperties=%s， %s", JSON.toJSONString(o), tClass.getName(), JSON.toJSONString(ignoreProperties), e.getMessage());
            log.error(errorMsg, e);
        }
        return null;
    }
}
