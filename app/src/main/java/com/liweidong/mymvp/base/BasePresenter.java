package com.liweidong.mymvp.base;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public interface BasePresenter <T extends BaseView>{

    void attachView(T baseView);

    void detachView();
}
