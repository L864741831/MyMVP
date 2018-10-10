package com.liweidong.mymvp.utils;


import com.liweidong.mymvp.model.http.exception.ApiException;
import com.liweidong.mymvp.model.http.response.GankHttpResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     *
     * 接口组成Flowables
     * FlowableTransformer
     * PARAM <上游>上游值类型

     *@ PARAM <顺流>下游值类型
     */
                                      //上游，下游
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程

        /*
*将函数应用于上游流程，并返回发布服务器
*可选的不同元素类型。
*@ PARAM上游上游流动实例
*@返回已转换的发布者实例
*/

        return new FlowableTransformer<T, T>() {
            @Override
            //     下游               上游
            public Flowable<T> apply(Flowable<T> observable) {

                //被观察io线程，线程调度观察者在主线程
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
                                                        //上游，下游
    public static <T> FlowableTransformer<GankHttpResponse<T>, T> handleResult() {   //compose判断结果

                /*
*将函数应用于上游流程，并返回发布服务器
*可选的不同元素类型。
*@ PARAM上游上游流动实例
*@返回已转换的发布者实例
*/

        return new FlowableTransformer<GankHttpResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<GankHttpResponse<T>> httpResponseFlowable) {

                /*
                FlatMap（）
作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
                 */

                return httpResponseFlowable.flatMap(new Function<GankHttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(GankHttpResponse<T> tGankHttpResponse) {
                        //返回是失败error为false
                        if(!tGankHttpResponse.getError()) {
                            //重新创建Flowable并发送onNext(results)数据
                            return createData(tGankHttpResponse.getResults());
                        } else {
                            //发送error事件
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });

            }
        };
    }


    /**
     * 生成Flowable
     *
     * rxjava2的Flowable支持背压
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
        //添加背压策略封装好的方法，此处选择Buffer模式，即缓存区大小无限制，但要注意内存
        //解决Flowable(被观察者发送事件速度大于观察者消费事件的速度，导致缓存区溢出)
    }




}
