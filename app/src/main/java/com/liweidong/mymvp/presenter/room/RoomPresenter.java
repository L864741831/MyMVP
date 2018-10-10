package com.liweidong.mymvp.presenter.room;

import android.content.Context;
import android.support.annotation.NonNull;

import com.liweidong.mymvp.base.BaseMvpPresenter;
import com.liweidong.mymvp.base.contract.room.RoomContract;
import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.bean.PhoneBean;
import com.liweidong.mymvp.model.db.PhoneDatabase;
import com.liweidong.mymvp.model.http.response.GankHttpResponse;
import com.liweidong.mymvp.utils.RxUtil;
import com.liweidong.mymvp.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class RoomPresenter extends BaseMvpPresenter<RoomContract.View>
        implements RoomContract.Presenter {

    //需要在构造方法@Inject  RoomActivity才能使用
    @Inject
    public RoomPresenter() {

    }

    //插入数据
    @Override
    public void insertAll(final Context context, final List<PhoneBean> phoneBean) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PhoneDatabase.getDefault(context).getPhoneDao().insertAll(phoneBean);
            }
        }).start();

    }

    //查询数据 rxjava线程调度
    @Override
    public void getAllPhone(Context context) {

        PhoneDatabase.getDefault(context).getPhoneDao().getPhoneAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<List<PhoneBean>>(baseView) {
            @Override
            public void onNext(List<PhoneBean> phoneBean) {
                baseView.showAllPhone(phoneBean);
            }
        });
    }



}
