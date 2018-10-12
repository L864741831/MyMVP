package com.liweidong.mymvp.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.liweidong.mymvp.app.App;
import com.liweidong.mymvp.di.component.ActivityComponent;
import com.liweidong.mymvp.di.component.DaggerActivityComponent;
import com.liweidong.mymvp.di.module.ActivityModule;
import com.liweidong.mymvp.utils.ToastUtils;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author:李炜东
 * Date:2018/8/31.
 * WX:17337162963
 * Email:18037891572@163.com
 *
 * 无MVP的Activity基类
 */

public abstract class BaseActivity extends SupportActivity implements BaseView, View.OnClickListener {

    protected Activity mContext;

    private AlertDialog loadingDialog;

    private final String TAG = this.getClass().getName();

    //创建视图绑定view
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //强制竖屏(不强制加)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.i(TAG, this.getClass().getName() + "------>onCreate");

        mContext = this;

        init();

        //设置布局
        setContentView(getLayout());

        //绑定初始化ButterKnife（一定要在setContentView之后）
        ButterKnife.bind(this);

        //初始化View
        initView();
        //获得intent数据
        initData();
        //设置事件(事件监听等)
        initEvent();
    }

    protected void init() {
        App.getInstance().registerActivity(this);
    }

    protected abstract int getLayout();

    protected void initView() {
    }

    protected void initData() {
    }

    protected void initEvent() {
    }

    //启动使试图可见，该方法快速
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, this.getClass().getName() + "------>onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, this.getClass().getName() + "------>onRestart");
    }

    //用户获取焦点，大多数初始化和操作
    //onPause后重新获取数据或初次获取数据
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, this.getClass().getName() + "------>onResume");
    }

    //失去焦点,但可能部分可见，例如多窗口模式
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, this.getClass().getName() + "------>onPause");
    }

    //完全不可见,在运行时没机会保存数据库可以在这里保存
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, this.getClass().getName() + "------>onStop");
    }

    //销毁当前界面,但进程中可能保留activity和视图对象
    //当界面finash或返回键销毁，则完全销毁
    //当因为界面发生变化，如屏幕旋转，则会保留成员变量或View中数据，如EditText中已经输入内容，保存在Bundle savedInstanceState
    //view需要设置id才能保留view中数据
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, this.getClass().getName() + "------>onDestroy");

        App.getInstance().unregisterActivity(this);
    }


    /**
     * Toast 提示用户
     *
     * @param msg 提示内容String
     */
    public void showTipMsg(String msg) {
        ToastUtils.showTipMsg(msg);
    }

    /**
     * Toast 提示用户
     *
     * @param msg 提示内容res目录下面的String的int值
     */
    public void showTipMsg(int msg) {
        ToastUtils.showTipMsg(msg);
    }


    /**
     * 设置点击事件.
     *
     * @param views 被点击View
     */
    public void setOnClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }


    /**
     * findViewById
     */
    public <T extends View> T findView(int resId) {
        return (T) super.findViewById(resId);
    }


    /**
     * Finish当前页面，最好实现onBackPressedSupport()，这个方法会有一个退栈操作，
     * 开源框架实现的，我们不用管
     */
    public void myFinish() {
        onBackPressedSupport();
    }

    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }


    public void invalidToken() {
        //用于检测你当前用户的token是否有效，无效就返回登录界面，具体的业务逻辑你自己实现
        //如果需要做到实时检测，推荐用socket长连接，每隔10秒发送一个验证当前登录用户token是否过期的请求
    }



    /**
     * 网络请求的时候显示正在加载的对话框
     */
    public void showLoading() {
        if (null == loadingDialog) {
            loadingDialog = new AlertDialog.Builder(this).setView(new ProgressBar(this)).create();
            loadingDialog.setCanceledOnTouchOutside(false);
            Window window = loadingDialog.getWindow();
            if (null != window) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }


    /**
     * 网络请求完成时隐藏加载对话框
     */
    public void hideLoading() {
        if (null != loadingDialog) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }
    }


    //准备将AppModule与HttpModule与ActivityModule工厂类与ActivityComponent中inject标注注入类的对象注入到指定位置
    protected ActivityComponent getActivityComponent(){
        return  DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule();
    }

}
