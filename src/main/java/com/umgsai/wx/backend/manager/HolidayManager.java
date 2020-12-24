package com.umgsai.wx.backend.manager;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author Shangyidong
 * 2020/8/3 17:21
 */
@Slf4j
@Component
@Data
public class HolidayManager {

    // 法律规定的放假日期
    private Set<String> lawHolidays = Sets.newHashSet("2021-01-01",
            "2021-02-11",
            "2021-02-12",
            "2021-02-15",
            "2021-02-16",
            "2021-02-17");

    // 由于放假需要额外工作的周末
    private Set<String> extraWorkdays = Sets.newHashSet(
            "2020-12-19",
            "2021-01-09",
            "2021-01-23",
            "2021-02-07",
            "2021-02-20",
            "2021-03-13",
            "2021-03-27");

    /**
     * 判断是否是法定假日
     */
    public boolean isLawHoliday(String calendar) throws Exception {
        isValidDate(calendar);
        return lawHolidays.contains(calendar);
    }

    public boolean isWeekends(String calendar) throws Exception {
        isValidDate(calendar);
        // 先将字符串类型的日期转换为Calendar类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(calendar);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 判断是否是需要额外补班的周末
     **/
    public boolean isExtraWorkday(String calendar) throws Exception {
        isValidDate(calendar);
        return extraWorkdays.contains(calendar);
    }

    /**
     * 判断是否是休息日（包含法定节假日和不需要补班的周末）
     **/
    public boolean isHoliday(String calendar) throws Exception {
        isValidDate(calendar);
        // 首先法定节假日必定是休息日
        if (isLawHoliday(calendar)) {
            return true;
        }
        // 排除法定节假日外的非周末必定是工作日
        if (!isWeekends(calendar)) {
            return false;
        }
        // 所有周末中只有非补班的才是休息日
        return !isExtraWorkday(calendar);
    }

    /**
     * 校验字符串是否为指定的日期格式,含逻辑严格校验,2007/02/30返回false
     **/
    private static void isValidDate(String str) throws Exception {
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy-MM-dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            log.error("ValidDate error, str={}", str, e);
            throw e;
        }
    }
}
