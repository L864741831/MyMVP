package com.liweidong.mymvp.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.liweidong.mymvp.model.bean.PhoneBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

@Dao
public interface PhoneDao {
    /**
     * 查询所有
     *
     * @return
     */
    @Query("SELECT * FROM PHONE")
    Flowable<List<PhoneBean>> getPhoneAll();

    /**
     * 根据指定字段查询
     *
     * @return
     */
    @Query("SELECT * FROM PHONE WHERE phone = :phone")
    List<PhoneBean> loadPhoneByIds(String phone);

    /**
     * 项数据库添加数据
     *
     * @param phone
     * 插入冲突解决方式为替换
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PhoneBean> phone);

    /**
     * 修改数据
     *
     * @param phone
     */
    @Update()
    void update(PhoneBean phone);

    /**
     * 删除数据
     *
     * @param phoneBean
     */
    @Delete()
    void delete(PhoneBean phoneBean);
}
