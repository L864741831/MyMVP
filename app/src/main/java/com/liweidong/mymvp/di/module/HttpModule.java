package com.liweidong.mymvp.di.module;

import android.provider.SyncStateContract;

import com.liweidong.mymvp.BuildConfig;
import com.liweidong.mymvp.app.Constants;
import com.liweidong.mymvp.di.qualifier.GankUrl;
import com.liweidong.mymvp.model.http.api.GankApis;
import com.liweidong.mymvp.utils.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

@Module
public class HttpModule {

    //6.（注入DataManager过程）找到HttpModule中Retrofit provideGankRetrofit
    //@GankUrl表示使用用@GankUrl标注的Retrofit provideGankRetrofit对象，避免多个Retrofit对象导致注入迷失
    @Singleton
    @Provides
    GankApis provideGankService(@GankUrl Retrofit retrofit) {
        return retrofit.create(GankApis.class);
    }

    //7.（注入DataManager过程）找到HttpModule中Retrofit.Builder  OkHttpClient provideClient
    @Singleton
    @Provides
    @GankUrl
    Retrofit provideGankRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GankApis.HOST);
    }


    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    //8.（注入DataManager过程）创建一个Retrofit.Builder并返回
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }



    //9.（注入DataManager过程）找到HttpModule中OkHttpClient.Builder provideOkHttpBuilder()
    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            //消息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            //BODY最全
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        //新建缓存目录下data文件夹中NetCache文件
        //设置缓存路径
        File cacheFile = new File(Constants.PATH_CACHE);
        // 1K=1024位 也就是 1k=1024bit ; 字节的英文是Byte; 1M=1024K 1G=1024M 1T=1024G
        //设置最大缓存为1byte * 1024 * 1024 * 50 = 50M
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

        //okhttp3 通过拦截器（Interceptor）两种缓存（离线和在线）
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //获取请求
                Request request = chain.request();
                //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            /*
                            从缓存取
                             */
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                //获取响应
                Response response = chain.proceed(request);

                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 60;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    //秒*60*60*24*28 == 28天
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                }
                return response;
            }
        };
        //拦截器添加请求头
//        Interceptor apikey = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                request = request.newBuilder()
//                        .addHeader("apikey",Constants.KEY_API)
//                        .build();
//                return chain.proceed(request);
//            }
//        }
//        设置统一的请求头部参数
//        builder.addInterceptor(apikey);

        //设置缓存
        //网络拦截器 可以添加、删除或替换请求头信息，还可以改变的请求携带的实体
        builder.addNetworkInterceptor(cacheInterceptor);
        //应用拦截器 用于查看请求信息及返回信息，如链接地址、头信息、参数信息等
        builder.addInterceptor(cacheInterceptor);

        //缓存目录和最大缓存50M
        builder.cache(cache);
        //设置超时
        /*
        枚举常量摘要
MICROSECONDS    微秒   一百万分之一秒（就是毫秒/1000）
MILLISECONDS    毫秒   千分之一秒
NANOSECONDS   毫微秒  十亿分之一秒（就是微秒/1000）
SECONDS          秒
MINUTES     分钟
HOURS      小时
DAYS      天
         */
        //连接时间10秒
        builder.connectTimeout(10, TimeUnit.SECONDS);
        //读取时间20秒
        builder.readTimeout(20, TimeUnit.SECONDS);
        //写入时间20秒
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        //重试 在 连接 失败
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }



    //10.（注入DataManager过程）创建一个OkHttpClient.Builder并返回
    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }








}
