package com.liweidong.mymvp.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.liweidong.mymvp.app.App;


/**
 * Author:李炜东
 * Date:2018/8/31.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public final class ToastUtils {
  private static Toast toast;

  private ToastUtils() {
    throw new RuntimeException("工具类不允许创建对象");
  }

  @SuppressWarnings("all")
  private static void init() {
    if (toast == null) {
      toast = Toast.makeText(App.getInstance(), "", Toast.LENGTH_SHORT);
    }
  }

  public static void showTipMsg(String msg) {
    if (null == toast) {
      init();
    }
    toast.setText(msg);
    toast.show();
  }

  public static void showTipMsg(@StringRes int msg) {
    if (null == toast) {
      init();
    }
    toast.setText(msg);
    toast.show();
  }
}
