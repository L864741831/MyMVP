package com.liweidong.mymvp.ui.main.fragment;

import android.util.Log;
import android.view.View;

import com.liweidong.mymvp.R;
import com.liweidong.mymvp.base.BaseFragment;

/**
 * Author:李炜东
 * Date:2018/9/5.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class Cfragment  extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c;
    }

    public void initView(){
        super.initView();

        Log.i("BaseFragment"+this.getClass().getName(),"initView");

    }

    public void initData(){
        super.initData();

        Log.i("BaseFragment"+this.getClass().getName(),"initData");

    }

    public void initEvent(){
        super.initEvent();

        Log.i("BaseFragment"+this.getClass().getName(),"initEvent");

    }

    @Override
    public void onClick(View v) {

    }
}
