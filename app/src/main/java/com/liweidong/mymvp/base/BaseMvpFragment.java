package com.liweidong.mymvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liweidong.mymvp.app.App;
import com.liweidong.mymvp.di.component.DaggerFragmentComponent;
import com.liweidong.mymvp.di.component.FragmentComponent;
import com.liweidong.mymvp.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * Author:李炜东
 * Date:2018/9/5.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {
    @Inject
    protected T basePresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        basePresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (basePresenter != null) {
            basePresenter.detachView();
        }
        super.onDestroyView();
    }

    protected abstract void initInject();

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule();
    }

}
