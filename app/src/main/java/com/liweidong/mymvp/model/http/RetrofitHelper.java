package com.liweidong.mymvp.model.http;

import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.http.api.GankApis;
import com.liweidong.mymvp.model.http.response.GankHttpResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class RetrofitHelper implements HttpHelper{

    private GankApis mGankApiService;

    //5.（注入DataManager过程）找到HttpModule中GankApis provideGankService
    @Inject
    public RetrofitHelper(GankApis gankApiService) {
        this.mGankApiService = gankApiService;
    }

    @Override
    public Flowable<GankHttpResponse<List<GankItemBean>>> fetchGirlList(int num, int page) {
        return mGankApiService.getGirlList(num, page);
    }
}
