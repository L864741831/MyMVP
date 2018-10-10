package com.liweidong.mymvp.model;

import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.http.HttpHelper;
import com.liweidong.mymvp.model.http.response.GankHttpResponse;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class DataManager implements HttpHelper {

    HttpHelper mHttpHelper;

    public DataManager(HttpHelper httpHelper) {
        mHttpHelper = httpHelper;
    }

    @Override
    public Flowable<GankHttpResponse<List<GankItemBean>>> fetchGirlList(int num, int page) {
        return mHttpHelper.fetchGirlList(num, page);
    }

}
