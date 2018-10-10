package com.liweidong.mymvp.di.component;

import com.liweidong.mymvp.app.App;
import com.liweidong.mymvp.di.module.AppModule;
import com.liweidong.mymvp.di.module.HttpModule;
import com.liweidong.mymvp.model.DataManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    //App getContext();  // 提供App的Context

    //2.（注入DataManager过程）找到AppModule中DataManager provideDataManager
    DataManager getDataManager(); //数据中心

}
