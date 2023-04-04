package cn.coffee.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    /**
     * 得到几分钟后的时间
     * @param d
     * @param minute
     * @return
     */
    public static Date getMinuteAfter(Date d, int minute) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minute);
        return now.getTime();
    }


}
