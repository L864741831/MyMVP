package com.liweidong.mymvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liweidong.mymvp.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author:李炜东
 * Date:2018/9/4.
 * WX:17337162963
 * Email:18037891572@163.com
 *
 * 无MVP的Fragment基类
 */

public abstract class BaseFragment extends SupportFragment implements BaseView,
        View.OnClickListener{

    private final String TAG = this.getClass().getName();

    protected Context mContext;

    protected View mView;

    //boolean有默认类型，是false
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕 true初始化过
    private boolean hasFetchData; // 标识已经触发过懒加载数据   true加载过

    private Unbinder unbinder;

    /*
    执行该方法时，Fragment与Activity已经完成绑定，该方法有一个Activity类型的参数，
    代表绑定的Activity，这时候你可以执行诸如mActivity = activity的操作
     */
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        Log.i(TAG, this.getClass().getName() + "------>onAttach");
        if (mContext != null) {
            this.mContext = mContext;
        } else {
            this.mContext = getActivity();
        }
    }

    /*
    初始化Fragment。可通过参数savedInstanceState获取之前保存的值
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, this.getClass().getName() + "------>onCreate");
    }


    /*
       创建 Fragment 需要显示的 View，默认返回 null。
     * 当返回的 View 不为 null 时，View 被销毁时会调用 onDestroyView()
     *
     * 初始化Fragment的布局。加载布局和findViewById的操作通常在此函数内完成，但是不建议执行耗时的操作，比如读取数据库数据列表。
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, this.getClass().getName() + "------>onCreateView");
        mView = inflater.inflate(getLayoutId(), null);

        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, mView);

        initView();

        return mView;
    }


    /*
    setUserVisibleHint(boolean isVisibleToUser)
    如果这个片段的UI当前对用户可见isVisibleToUser为真，否则假

    setUserVisibleHint：在切换到被预加载调用过setUserVisibleHint的片段时，当前被切换到的片段 会 再次调用自己的setUserVisibleHint，并调用“相邻片段的”setUserVisibleHint。

    onViewCreated：在切换到被预加载调用过onViewCreated的片段时，当前被切换到的片段 不会 再次调用自己的onViewCreated，只调用“相邻片段的”没调用过onViewCreated方法的onViewCreated方法。
     */
    //针对父布局为滚动性(如ViewPage,RecyclerView)片段再次可见
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);

        Log.i(getClass().getName()+"123456", "进入setUserVisibleHint未判断是否可见");

        if (isVisibleToUser) {
            Log.i(getClass().getName()+"123456", "进入setUserVisibleHint并且可见");
            /*
            1.(当前片段是默认显示的第一个片段)视图未初始化完成，不能加载数据，继续调用onViewCreated

            3.(首次切换到当前片段)视图未初始化完毕isViewPrepared为false 不能加载数据，继续onViewCreated(如果预初始化过则成功加载数据，再onViewCreated中因为加载过数据无法再次加载)

              5.(再次切换回当前片段)加载过数据hasFetchData为true不能加载数据，继续onViewCreated
             */
            lazyFetchDataIfPrepared();
        }
    }

    /*
    该方法在 onCreateView() 之后会被立即执行
    此时可以对 View 对象进行赋值，当然在 onCreateView() 中也可以执行
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, this.getClass().getName() + "------>onViewCreated");

        Log.i(getClass().getName()+"123456", "进入onViewCreated");

        /*
        2.(当前片段是默认显示的第一个片段)视图可见，视图初始化完成，未加载过数据，成功加载数据。

        4.(首次切换到当前片段)片段可见，未加载过数据，视图初始化完毕，成功加载数据。(如果预初始化过则成功在setUserVisibleHint加载数据，在onViewCreated中因为加载过数据无法再次加载)

        6.(再次切换回当前片段)加载过数据hasFetchData为true不能加载数据，直接显示

        7.(界面在onDestroyView销毁与fragment相关视图，视图初始化完毕为false，数据加载过为false,重新进入步骤一)

        8.(预初始化时(如ViewPage缓存时)片段不可见时，无法加载数据，只能初始化界面)
         */
        //视图初始化完毕
        isViewPrepared = true;

        //加载数据(视图不可见时无法加载)
        lazyFetchDataIfPrepared();
    }


/*
getUserVisibleHint()
向系统设置一个提示，说明该片段的UI是否当前可见。
 */
    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }



    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {
        //Log.(TAG, getClass().getName() + "------>lazyFetchData");
        Log.i(getClass().getName()+"123456", "成功加载数据");

        initData();
    }


    /*
执行该方法时，与Fragment绑定的Activity的onCreate方法已经执行完成并返回，在该方法内可以进行与Activity交互的UI操作，
所以在该方法之前Activity的onCreate方法并未执行完成，如果提前进行交互操作，会引发空指针异常。
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, this.getClass().getName() + "------>onActivityCreated");
        initEvent();
    }



    /*
    执行该方法时，Fragment由不可见变为可见状态。
     */
    public void onStart() {
        super.onStart();
        Log.i(TAG, this.getClass().getName() + "------>onStart");
    }

    /*
    执行该方法时，Fragment处于活动状态，用户可与之交互。
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, this.getClass().getName() + "------>onResume");

/*        //没有懒加载(测试：在进入A片段时会去加载B片段的onResume，提前加载数据)
        Log.i("123789", this.getClass().getName() + "------>onResume");
        lazyFetchData();*/
    }

    /*
执行该方法时，Fragment处于暂停状态，但依然可见，用户不能与之交互。
 */
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, this.getClass().getName() + "------>onPause");
    }

    /*
执行该方法时，Fragment完全不可见。
*/
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, this.getClass().getName() + "------>onStop");
    }

    /*
销毁与Fragment有关的视图，但未与Activity解除绑定，依然可以通过onCreateView方法重新创建视图。
通常在ViewPager+Fragment的方式下会调用此方法。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, this.getClass().getName() + "------>onDestroyView");
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;

        //解绑
        unbinder.unbind();
    }

    /*
    销毁Fragment。通常按Back键退出或者Fragment被回收时调用此方法。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, this.getClass().getName() + "------>onDestroy");
    }

    /*
解除与Activity的绑定。在onDestroy方法之后调用。
 */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, this.getClass().getName() + "------>onDetach");
    }


    protected abstract int getLayoutId();

    protected void initView(){

    };

    protected void initData(){

    };

    protected void initEvent() {

    }


    protected <T extends View> T findView(@IdRes int id) {
        return mView.findViewById(id);
    }


    /**
     * 设置点击事件.
     *
     * @param ids 被点击View的ID
     */
    public void setOnClick(@IdRes int... ids) {
        for (int id : ids) {
            mView.findViewById(id).setOnClickListener(this);
        }
    }

    /**
     * 设置点击事件.
     *
     * @param views 被点击View的ID
     */
    public void setOnClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
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

}
