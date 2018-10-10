package com.liweidong.mymvp.presenter.main;

import com.liweidong.mymvp.base.BaseMvpPresenter;
import com.liweidong.mymvp.base.contract.main.MainContract;
import com.liweidong.mymvp.model.DataManager;
import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.http.response.GankHttpResponse;
import com.liweidong.mymvp.utils.RxUtil;
import com.liweidong.mymvp.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class MainPresenter extends BaseMvpPresenter<MainContract.View>
        implements MainContract.Presenter {


    private DataManager mDataManager;

    //1.（注入DataManager过程）找到AppComponent中DataManager getDataManager()；
    @Inject
    public MainPresenter(DataManager mDataManager){
        this.mDataManager = mDataManager;
    }

/*    mDataManager.getGirlList(num, page);  == mGankApiService.getGirlList(num, page); == retrofit.create(GankApis.class)
    ==builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()   .getGirlList(num, page);*/

    @Override
    public void getContent(int num,int page) {

        //compose替换一些可重用代码
        addSubscribe(mDataManager.fetchGirlList(num,page)
                //线程调度
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                //统一返回结果处理,表适状态的code，错误发送发送error事件，正确从新创建Flowable并添加背压策略
                .compose(RxUtil.<List<GankItemBean>>handleResult())
                //继承ResourceSubscriber重写onComplete完成，onError错误
                .subscribeWith(new CommonSubscriber<List<GankItemBean>>(baseView) {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        baseView.showContent(gankItemBeen);
                    }
                })
        );

    }

}
