package com.liweidong.mymvp.model.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

/*
外键非常强大，因为它们允许您指定更新引用实体时发生的情况。
例如，如果User通过onDelete = CASCADE 在 @ForeignKey 注释中包含来删除相应的实例，则可以告诉SQLite删除用户的所有书籍。
 */

@Entity(foreignKeys = @ForeignKey(entity = PhoneBean.class,
        parentColumns = "ID",
        childColumns = "user_id"))
public class Book {
    @PrimaryKey
    public int bookId;

    public String title;

    @ColumnInfo(name = "user_id")
    public int userId;
}
