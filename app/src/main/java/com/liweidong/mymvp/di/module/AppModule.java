package com.liweidong.mymvp.di.module;

import com.liweidong.mymvp.app.App;
import com.liweidong.mymvp.model.DataManager;
import com.liweidong.mymvp.model.http.HttpHelper;
import com.liweidong.mymvp.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

@Module
public class AppModule {

/*    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }*/


    //3.（注入DataManager过程）找到AppModule中HttpHelper provideHttpHelper
    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper) {
        return new DataManager(httpHelper);
    }

    //4.（注入DataManager过程）找到RetrofitHelper中public RetrofitHelper构造函数
    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }



}
