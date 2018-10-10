package com.liweidong.mymvp.model.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class ConversionFactory {
        /*
    如果我们想持久化日期的实例，我们可以写如下TypeConverter去存储相等的Unix时间戳在数据库中
     */

    @TypeConverter
    public static Long fromDateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromLongToDate(Long value) {
        return value == null ? null : new Date(value);
    }

}
