package com.liweidong.mymvp.ui.main.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liweidong.mymvp.R;
import com.liweidong.mymvp.base.BaseFragment;
import com.liweidong.mymvp.model.event.EventBusStickyMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Author:李炜东
 * Date:2018/9/5.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class Bfragment extends BaseFragment {
    @BindView(R.id.frag_b)
    TextView fragB;

    boolean is_register = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }

    public void initView() {
        super.initView();

        if(!is_register){
            //注册,注意不要进行多次注册,否则程序可能崩溃.可以设置一个标志.
            //(到D片段再回C片段时会再次调用B片段的initView方法，如果再次注册程序会崩溃)
            EventBus.getDefault().register(this);
            is_register = true;
        }



        Log.i("BaseFragment" + this.getClass().getName(), "initView");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // 移除所有的粘性事件.
        EventBus.getDefault().removeAllStickyEvents();
        // 解注册
        EventBus.getDefault().unregister(this);
    }


    //获取粘性事件Afragment发布的
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onStickyEvent(EventBusStickyMsg event) {
        fragB.setText("Sticky 数据 : " + event.name);
    }


    public void initData() {
        super.initData();

        Log.i("BaseFragment" + this.getClass().getName(), "initData");

    }

    public void initEvent() {
        super.initEvent();

        Log.i("BaseFragment" + this.getClass().getName(), "initEvent");

    }

    @Override
    public void onClick(View v) {

    }
}
