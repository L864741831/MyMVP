package com.liweidong.mymvp.base.contract.main;

import com.liweidong.mymvp.base.BasePresenter;
import com.liweidong.mymvp.base.BaseView;
import com.liweidong.mymvp.model.bean.GankItemBean;

import java.util.List;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public interface MainContract {

    interface View extends BaseView {
        void showContent(List<GankItemBean> gankItemBeen);
    }

    interface Presenter extends BasePresenter<View> {
        void getContent(int num,int page);
    }

}
