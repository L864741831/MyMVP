package com.liweidong.mymvp.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liweidong.mymvp.R;
import com.liweidong.mymvp.base.BaseMvpActivity;
import com.liweidong.mymvp.base.contract.main.MainContract;
import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.event.EventBusMsg;
import com.liweidong.mymvp.presenter.main.MainPresenter;
import com.liweidong.mymvp.ui.main.adapter.ContentPagerAdapter;
import com.liweidong.mymvp.ui.main.fragment.Afragment;
import com.liweidong.mymvp.ui.main.fragment.Bfragment;
import com.liweidong.mymvp.ui.main.fragment.Cfragment;
import com.liweidong.mymvp.ui.main.fragment.Dfragment;
import com.liweidong.mymvp.ui.room.activity.RoomActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {


    ContentPagerAdapter mPagerAdapter;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.vp_test)
    ViewPager vpTest;
    @BindView(R.id.tv_room)
    TextView tvRoom;


    protected int getLayout() {
        return R.layout.activity_main;
    }


/*    public void initView() {
        super.initView();

        //test = findView(R.id.test);
        vp_test = findView(R.id.vp_test);
    }*/

    //将AppModule与HttpModule与ActivityModule工厂类中的对象注入到MainActivity
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @SuppressLint("SetTextI18n")
    public void initData() {
        super.initData();
        test.setText("aaaaaaaaaaa");

        List<Fragment> fragments = initFragments();
        mPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), fragments);
        vpTest.setAdapter(mPagerAdapter);


        /*
        设置当前page左右两侧应该被保持的page数量，超过这个限制，page会被销毁重建（只是销毁视图），onDestroy-onCreateView,但不会执行onDestroy。尽量维持这个值小，
        特别是有复杂布局的时候，因为如果这个值很大，就会占用很多内存，如果只有3-4page的话，可以全部保持active，可以保持page切换的顺滑
         */
        vpTest.setOffscreenPageLimit(3);
    }

/*    public void initEvent() {
        super.initEvent();
        setOnClick(test);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 注册订阅者
        EventBus.getDefault().register(MainActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 2. 解注册订阅者(不要忘记解注册否则可能会造成内存泄漏.)
        EventBus.getDefault().unregister(MainActivity.this);
    }


    /*
    这中用法一定要先订阅事件在进行发布事件才可以接收到事件,当然也可以实现先发布在订阅的模式（粘性事件）

    ThreadMode : POSTING
POSTING是 threadMode的默认参数,如果不设置该参数就是POSTING模式. 在这种模式下接收事件的方法和发布消息的方法运行在同一个线程中.
优点 : 这种模式下开销最小,因为不需要切换线程.

ThreadMode : MAIN
在 MAIN 模式下事件接收方法将会运行在主线程中(有时也称为UI线程). 如果发布事件的线程是主线程那么事件就会直接发布给订阅者,不会进行线程切换, 也就相当于使用了POSTING模式.
使用注意 : 在此模式不要在事件接收方法中进行耗时操作,防止UI线程阻塞

ThreadMode : BACKGROUND
事件接收方法将会运行在一个后台线程中,但是事件不可以并发执行.
如果发布事件本身就在后台线程中,那么事件接收方法就运行在这个线程中,不在另外开启新的线程.
如果发布事件在主线程中,那么EventBus会开一个后台线程(只有一个)然后在该线程中依次处理所有事件.
使用注意 : 事件处理方法应当尽快返回,避免阻塞后台线程.

ThreadMode : ASYNC
在ASYNC模式下事件接收方法总是运行在一个发布事件线程和主线程之外独立的线程中,发布事件不必依赖订阅事件处理结果.
因此此模式适合于在事件处理中有比较耗时的操作的情况.为了避免同一时间有过多的运行线程.EventBus使用线程池和事件处理完成通知来限制最大的线程并发数.

     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMsg event) {
        Log.d("eventbus", "接收到信息");
        test.setText("EventBus 数据 : " + event.name);
    }


    @OnClick({R.id.test,R.id.tv_room})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test:
                //showTipMsg("233");
                //showTipMsg(R.string.app_name);

                //App.getInstance().exitApp();
                startActivity(new Intent(this, BActivity.class));

                //showLoading();

                //myFinish();

                //basePresenter.getContent(1, 10);


                break;

            case R.id.tv_room:

                startActivity(new Intent(this, RoomActivity.class));


                break;


        }
    }


    @Override
    public void showContent(List<GankItemBean> gankItemBeen) {
        test.setText(gankItemBeen.toString());
    }


    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        Fragment fragment1 = new Afragment();
        Fragment fragment2 = new Bfragment();
        Fragment fragment3 = new Cfragment();
        Fragment mineFragment = new Dfragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(mineFragment);
        return fragments;
    }


}
