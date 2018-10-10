package com.liweidong.mymvp.base.contract.room;

import android.content.Context;

import com.liweidong.mymvp.base.BasePresenter;
import com.liweidong.mymvp.base.BaseView;
import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.bean.PhoneBean;

import java.util.List;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public interface RoomContract {

    interface View extends BaseView {
        void showAllPhone(List<PhoneBean> phoneBean);
    }

    interface Presenter extends BasePresenter<View> {
        void getAllPhone(Context context);
        void insertAll(Context context,List<PhoneBean> phoneBean);
    }

}
