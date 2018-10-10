package com.liweidong.mymvp.di.component;

import android.app.Activity;

import com.liweidong.mymvp.di.module.ActivityModule;
import com.liweidong.mymvp.ui.main.activity.BActivity;
import com.liweidong.mymvp.ui.main.activity.MainActivity;
import com.liweidong.mymvp.di.scope.ActivityScope;
import com.liweidong.mymvp.ui.room.activity.RoomActivity;

import dagger.Component;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //Activity getActivity();

    void inject(MainActivity mainActivity);
    void inject(BActivity bActivity);
    void inject(RoomActivity roomActivity);


}
