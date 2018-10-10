package com.liweidong.mymvp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:李炜东
 * Date:2018/9/5.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class TimeUtil {

    /**
     * 获取精确到秒的时间戳
     * 去掉后三位
     * C++或PHP中的时间戳是精确到秒的，但是Java中的时间戳是精确到毫秒的，多三位
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }


    //通过整除将最后的三位去掉
    public static int getSecondTimestampTwo(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return Integer.valueOf(timestamp);
    }


    /**
     * 时间戳转时间(10位时间戳)
     * @param time
     * @return
     */
    public static String timestampToDate(long time) {
        String dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLong = Long.valueOf(time);
        dateTime = simpleDateFormat.format(new Date(timeLong * 1000L));
        return dateTime;
    }

    /*
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
     */
}
