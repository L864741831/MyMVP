package com.liweidong.mymvp.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.liweidong.mymvp.model.bean.PhoneBean;
import com.liweidong.mymvp.model.dao.PhoneDao;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

/*
1.创建数据库表PhoneBean
2.创建类型转换ConversionFactory
3.创建PhoneDao操作数据库
4.单例创建数据库PhoneDatabase
5.使用          PhoneDatabase.getDefault(getApplicationContext()).getPhoneDao().insertAll(mPhones);



 */
/*
创建数据库

当一个类用 @Entity 注解并且被 @Database 注解中的 entities 属性所引用，Room就会在数据库中为这个被 @Entity 注解的类创建一张表。

entities说明数据库里有哪些表（实体），version是版本号，exportSchema是是否允许导出，默认为true（导出后的数据库什么样的，我们最后看看）
 */

@Database(entities = {PhoneBean.class}, version = 1, exportSchema = false)
@TypeConverters({ConversionFactory.class})
public abstract class PhoneDatabase extends RoomDatabase {

    /*
    单例模式创建RoomDatabase
     */
    public static PhoneDatabase getDefault(Context context) {
        return buildDatabase(context);
    }

    private static PhoneDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), PhoneDatabase.class, "USERPHONE.db")
                .allowMainThreadQueries()   //允许主线程查询(不推荐，最好在子线程)
                .build();
    }

    public abstract PhoneDao getPhoneDao();

}
