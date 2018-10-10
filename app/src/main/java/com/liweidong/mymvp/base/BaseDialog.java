package com.liweidong.mymvp.base;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.liweidong.mymvp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:李炜东
 * Date:2018/9/12.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public abstract class BaseDialog extends DialogFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置自定义样式
        if (setDialogStyle() == 0) {
            /*
               选择一个标准样式

                STYLE_NORMAL  样式正常
                STYLE NO TITLE  样式无标题
                STYLE NO FRAME  样式无框
                STYLE NO INPUT  样式无输入
             */
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.dialog_style);
        } else {
            setStyle(DialogFragment.STYLE_NO_FRAME, setDialogStyle());
        }
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // 设置进场动画
        if (setWindowAnimationsStyle() != 0) {
            getDialog().getWindow().setWindowAnimations(setWindowAnimationsStyle());
        }
        // return 视图
        View mView = inflater.inflate(getLayoutId(), container, false);

        return mView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(getArguments());
    }


    /**
     * 显示Dialog
     *
     * @param activity
     * @param bundle   要传递给Dialog的Bundle对象
     * @param tag      设置一个标签用来标记Dialog
     */
    public void show(Activity activity, Bundle bundle, String tag) {
        if (activity == null && isShowing()) return;
        //事务
        FragmentTransaction mTransaction = activity.getFragmentManager().beginTransaction();
        Fragment mFragment = activity.getFragmentManager().findFragmentByTag(tag);
        if (mFragment != null) {
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mTransaction.remove(mFragment);
        }
        if (bundle != null) {
            setArguments(bundle);
        }
        show(mTransaction, tag);
    }


    /**
     * 是否正显示
     *
     * @return false:isHidden  true:isShowing
     */
    public boolean isShowing() {
        return getShowsDialog();
    }


    /**
     * 设置弹出框样式
     *
     * 子类可重写改方法返回其他样式
     *
     * @return
     */
    protected int setDialogStyle() {
        return 0;
    }


    /**
     * 设置窗口转场动画
     *
     * 子类可重写改方法返回其他动画
     *
     * @return
     */
    protected int setWindowAnimationsStyle() {
        return 0;
    }


    /**
     * 自定义时添加layout
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     *
     * @param view
     */
    protected abstract void initView(View view);


    /**
     * 加载数据
     *
     * @param bundle 用这个Bundle对象接收传入时的Bundle对象
     */
    protected abstract void loadData(Bundle bundle);




    /**
     * 子类可在onStart中调用进行初始化
     *
     * 设置Dialog点击外部区域是否隐藏
     *
     * @param cancel
     */
    protected void setCanceledOnTouchOutside(boolean cancel) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(cancel);
        }
    }

    /**
     * 子类可在onStart中调用进行初始化
     *
     * 设置Dialog gravity
     *
     * @param gravity
     */
    protected void setGravity(int gravity) {
        if (getDialog() != null) {
            Window mWindow = getDialog().getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            //用于设置View中内容相对于View组件的对齐方式
            params.gravity = gravity;
            mWindow.setAttributes(params);
        }
    }

    /**
     * 子类可在onStart中调用进行初始化
     *
     * 设置Dialog窗口width
     *
     * @param width
     */
    protected void setDialogWidth(int width) {
        setDialogWidthAndHeight(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 子类可在onStart中调用进行初始化
     *
     * 设置Dialog窗口height
     *
     * @param height
     */
    protected void setDialogHeight(int height) {
        setDialogWidthAndHeight(LinearLayout.LayoutParams.WRAP_CONTENT, height);
    }

    /**
     * 子类可在onStart中调用进行初始化
     *
     * 设置Dialog窗口width，height
     *
     * @param width
     * @param height
     */
    protected void setDialogWidthAndHeight(int width, int height) {
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, height);
        }
    }



}
