package com.liweidong.mymvp.ui.main.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liweidong.mymvp.R;
import com.liweidong.mymvp.base.BaseMvpFragment;
import com.liweidong.mymvp.base.contract.main.MainContract;
import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.event.EventBusStickyMsg;
import com.liweidong.mymvp.presenter.main.MainPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:李炜东
 * Date:2018/9/5.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class Afragment extends BaseMvpFragment<MainPresenter> implements MainContract.View {


    @BindView(R.id.frag_a)
    TextView fragA;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a;
    }

    protected void initInject() {
        getFragmentComponent().inject(this);
    }
/*    public void initView(){
        super.initView();

        Log.i("BaseFragment"+this.getClass().getName(),"initView");

        frag_a = findView(R.id.frag_a);
    }*/

    public void initData() {
        super.initData();

        Log.i("BaseFragment" + this.getClass().getName(), "initData");

        //frag_a.setText("这是frag_a");

        basePresenter.getContent(1, 10);

        //发送粘性事件
        EventBus.getDefault().postSticky(new EventBusStickyMsg("我是Afragment发布的Sticky消息"));
    }

/*    public void initEvent(){
        super.initEvent();

        Log.i("BaseFragment"+this.getClass().getName(),"initEvent");

        setOnClick(fragA);
    }*/

    @OnClick({R.id.frag_a})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_a:
                //showTipMsg("这是frag_a的响应");

                //调用p层
                basePresenter.getContent(1, 10);
                break;
        }
    }


    @Override
    public void showContent(List<GankItemBean> gankItemBeen) {
        fragA.setText("这是调用p层获取的数据" + gankItemBeen.toString());
    }

}
