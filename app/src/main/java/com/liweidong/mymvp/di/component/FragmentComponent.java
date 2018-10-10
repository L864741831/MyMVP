package com.liweidong.mymvp.di.component;

import com.liweidong.mymvp.di.module.FragmentModule;
import com.liweidong.mymvp.di.scope.FragmentScope;
import com.liweidong.mymvp.ui.main.fragment.Afragment;

import dagger.Component;

/**
 * Author:李炜东
 * Date:2018/9/5.
 * WX:17337162963
 * Email:18037891572@163.com
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(Afragment aFragment);
}
