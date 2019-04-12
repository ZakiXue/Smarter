package com.example.fogfly.smarter.utils;

import java.util.Calendar;

/**
 * @author Zaki Xue
 * @time 2019/2/25 21:54
 * @description 年月日
 */
public class GetDate {

    private static String hour;
    private static String min;

    public static StringBuilder getDate() {

        StringBuilder stringBuilder = new StringBuilder();
        Calendar now = Calendar.getInstance();
        stringBuilder.append(now.get(Calendar.YEAR) + "年");
        stringBuilder.append((int) (now.get(Calendar.MONTH) + 1) + "月");
        stringBuilder.append(now.get(Calendar.DAY_OF_MONTH) + "日");
        if (now.get(Calendar.MINUTE) < 10) {
            min = "0" + now.get(Calendar.MINUTE);
        } else {
            min = "" + now.get(Calendar.MINUTE);
        }
        if (now.get(Calendar.HOUR_OF_DAY) < 10) {
            hour = "0" + now.get(Calendar.HOUR_OF_DAY);
        } else {
            hour = "" + now.get(Calendar.HOUR_OF_DAY);
        }
        stringBuilder.append(" " + hour + ":" + min);
        return stringBuilder;
    }
}
