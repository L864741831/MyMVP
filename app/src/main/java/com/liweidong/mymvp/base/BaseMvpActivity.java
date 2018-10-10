package com.liweidong.mymvp.base;

import javax.inject.Inject;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    @Inject
    protected T basePresenter;

    protected void initView() {
        super.initView();
        initInject();
        if (null != basePresenter) {
            basePresenter.attachView(this);
        }
    }

    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        if (null != basePresenter) {
            basePresenter.detachView();
            basePresenter = null;
        }
        super.onDestroy();
    }


}
