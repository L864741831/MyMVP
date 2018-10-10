package com.liweidong.mymvp.widget;

import android.text.TextUtils;
import android.util.Log;

import com.liweidong.mymvp.base.BaseView;
import com.liweidong.mymvp.model.http.exception.ApiException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    //传入BaseView使用BaseView定义的方法
    private BaseView mView;
    //传入错误提示信息
    private String mErrorMsg;
    //显示错误信息(统一设置所有是否显示错误提示)
    private boolean isShowErrorState = true;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }


    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (isShowErrorState) {

            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
                mView.showTipMsg(mErrorMsg);
                //Log.i("CommonSubscriber", "new CommonSubscriber传入错误提示信息"+mErrorMsg);
            } else if (e instanceof ApiException) {
                mView.showTipMsg(e.toString());
                //Log.i("CommonSubscriber", "自定义发送的Exception提示信息(如状态值为0表示失败，1为成功，返回的0)");
            } else if (e instanceof HttpException) {
                mView.showTipMsg("数据加载失败ヽ(≧Д≦)ノ");
                //Log.i("CommonSubscriber", "HttpException");
            } else {
                //mView.showTipMsg("未知错误ヽ(≧Д≦)ノ");
                //Log.i("CommonSubscriber", "其他异常提示信息");
                mView.showTipMsg("网络状态不好ヽ(≧Д≦)ノ");
            }

            //mView.showTipMsg("错误提示信息");
        }
    }
}

