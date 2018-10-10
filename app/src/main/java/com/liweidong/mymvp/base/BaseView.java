package com.liweidong.mymvp.base;

import android.support.annotation.StringRes;

/**
 * Author:李炜东
 * Date:2018/8/31.
 * WX:17337162963
 * Email:18037891572@163.com
 * <p>
 * View基类
 */

public interface BaseView {
    //显示短Toast
    void showTipMsg(String msg);
    void showTipMsg(@StringRes int msg);
}
