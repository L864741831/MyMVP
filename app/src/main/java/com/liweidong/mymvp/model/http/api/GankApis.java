package com.liweidong.mymvp.model.http.api;

import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.http.response.GankHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public interface GankApis {

    String HOST = "http://gank.io/api/";

    /**
     * 妹纸列表
     */
    @GET("data/福利/{num}/{page}")
    Flowable<GankHttpResponse<List<GankItemBean>>> getGirlList(@Path("num") int num, @Path("page") int page);

    //http://gank.io/api/data/福利/1/1


/*
    @POST("index.php/Pn/Index/TjList")
    @FormUrlEncoded
    Flowable<IndexHttpResponse<List<TjItemBean>>> testFormUrlEncoded1(@Field("time") String time, @Field("page") String page, @Field("gender") String gender, @Field("sign") String sign);
*/

}
