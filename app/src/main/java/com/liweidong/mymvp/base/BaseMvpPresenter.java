package com.liweidong.mymvp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class BaseMvpPresenter <T extends BaseView> implements BasePresenter<T> {

    protected T baseView;

    protected CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }


    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    public void attachView(T baseView) {
        this.baseView = baseView;
    }

    public void detachView() {
        this.baseView = null;
        unSubscribe();
    }




}
