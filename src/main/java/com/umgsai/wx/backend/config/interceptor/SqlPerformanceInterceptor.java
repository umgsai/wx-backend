package com.umgsai.wx.backend.config.interceptor;

import cn.hutool.core.date.SystemClock;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "query",
        args = {Statement.class, ResultHandler.class}
), @Signature(
        type = StatementHandler.class,
        method = "update",
        args = {Statement.class}
), @Signature(
        type = StatementHandler.class,
        method = "batch",
        args = {Statement.class}
)})
public class SqlPerformanceInterceptor implements Interceptor {

    // 最大耗时
    @Getter
    @Setter
    private long maxTime = 1000L;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) target;
        long start = SystemClock.now();
        Object result = invocation.proceed();
        long sqlCost = SystemClock.now() - start;
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        sql = formatSql(sql, parameterObject, parameterMappingList);
        if (sqlCost < this.maxTime) {
            log.info("SQL:{}  执行耗时:{}ms", sql, sqlCost);
        } else {
            log.info("SQL:{}  执行耗时:{}ms  超过最大执行时间", sql, sqlCost);
        }
        return result;
    }

    private String formatSql(String sql, Object parameterObject, List<ParameterMapping> parameterMappingList) {
        if (StringUtils.isBlank(sql)) {
            return "";
        }

        sql = beautifySql(sql);

        if (parameterObject == null || CollectionUtils.isEmpty(parameterMappingList)) {
            return sql;
        }

        try {
            Class<?> parameterObjectClass = parameterObject.getClass();
            if (this.isStrictMap(parameterObjectClass)) {
                DefaultSqlSession.StrictMap<Collection<?>> strictMap = (DefaultSqlSession.StrictMap) parameterObject;
                if (isList(((Collection) strictMap.get("list")).getClass())) {
                    sql = this.handleListParameter(sql, strictMap.get("list"));
                }
            } else if (this.isMap(parameterObjectClass)) {
                Map<?, ?> paramMap = (Map<?, ?>) parameterObject;
                sql = this.handleMapParameter(sql, paramMap, parameterMappingList);
            } else {
                sql = this.handleCommonParameter(sql, parameterMappingList, parameterObjectClass, parameterObject);
            }

            return sql;
        } catch (Exception var7) {
            return sql;
        }

    }

    private String beautifySql(String sql) {
        sql = sql.replaceAll("[\\s\n ]+", " ");
        return sql;
    }

    private String handleListParameter(String sql, Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return sql;
        }
        String value;

        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext(); sql = sql.replaceFirst("\\?", value)) {
            Object obj = iterator.next();
            value = null;
            Class<?> objClass = obj.getClass();
            if (this.isPrimitiveOrPrimitiveWrapper(objClass)) {
                value = obj.toString();
            } else if (objClass.isAssignableFrom(String.class)) {
                value = "\"" + obj.toString() + "\"";
            }
        }

        return sql;
    }

    private String handleMapParameter(String sql, Map<?, ?> paramMap, List<ParameterMapping> parameterMappingList) {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Object propertyName = parameterMapping.getProperty();
            Object propertyValue = paramMap.get(propertyName);
            if (propertyValue != null) {
                if (propertyValue.getClass().isAssignableFrom(String.class)) {
                    propertyValue = "\"" + propertyValue + "\"";
                }
                sql = sql.replaceFirst("\\?", propertyValue.toString());
            }
        }
        return sql;
    }

    private String handleCommonParameter(String sql, List<ParameterMapping> parameterMappingList, Class<?> parameterObjectClass, Object parameterObject) throws Exception {
        String propertyValue;
        for (Iterator<ParameterMapping> iterator = parameterMappingList.iterator(); iterator.hasNext(); sql = sql.replaceFirst("\\?", propertyValue)) {
            ParameterMapping parameterMapping = (ParameterMapping) iterator.next();

            if (isPrimitiveOrPrimitiveWrapper(parameterObjectClass)) {
                propertyValue = parameterObject.toString();
                continue;
            }
            String propertyName = parameterMapping.getProperty();
            Field field = parameterObjectClass.getDeclaredField(propertyName);
            field.setAccessible(true);
            propertyValue = String.valueOf(field.get(parameterObject));
            if (parameterMapping.getJavaType().isAssignableFrom(String.class)) {
                propertyValue = "\"" + propertyValue + "\"";
            }
        }
        return sql;
    }

    private boolean isPrimitiveOrPrimitiveWrapper(Class<?> parameterObjectClass) {
        return parameterObjectClass.isPrimitive() || parameterObjectClass.isAssignableFrom(Byte.class) || parameterObjectClass.isAssignableFrom(Short.class) || parameterObjectClass.isAssignableFrom(Integer.class) || parameterObjectClass.isAssignableFrom(Long.class) || parameterObjectClass.isAssignableFrom(Double.class) || parameterObjectClass.isAssignableFrom(Float.class) || parameterObjectClass.isAssignableFrom(Character.class) || parameterObjectClass.isAssignableFrom(Boolean.class);
    }

    private boolean isStrictMap(Class<?> parameterObjectClass) {
        return parameterObjectClass.isAssignableFrom(DefaultSqlSession.StrictMap.class);
    }

    private boolean isList(Class<?> clazz) {
        Class<?>[] interfaceClasses = clazz.getInterfaces();

        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(List.class)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMap(Class<?> parameterObjectClass) {
        Class<?>[] interfaceClasses = parameterObjectClass.getInterfaces();

        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(Map.class)) {
                return true;
            }
        }
        return false;
    }

}
