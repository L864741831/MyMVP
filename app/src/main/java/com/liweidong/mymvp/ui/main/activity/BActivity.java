package com.liweidong.mymvp.ui.main.activity;

import android.view.View;
import android.widget.TextView;

import com.liweidong.mymvp.R;
import com.liweidong.mymvp.base.BaseMvpActivity;
import com.liweidong.mymvp.base.contract.main.MainContract;
import com.liweidong.mymvp.model.bean.GankItemBean;
import com.liweidong.mymvp.model.event.EventBusMsg;
import com.liweidong.mymvp.presenter.main.MainPresenter;
import com.liweidong.mymvp.ui.main.adapter.RecyclerviewAdapter;
import com.liweidong.mymvp.widget.EasyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/*
测试
android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

        的活动
 */
public class BActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.test_b)
    TextView testB;
    @BindView(R.id.easy_recy)
    EasyRecyclerView easyRecy;

    List<Map<String, Object>> list = new ArrayList<>();
    RecyclerviewAdapter adapter;

    int num = 10;   //每页数量
    int page = 1;   //页码
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayout() {
        return R.layout.b;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    public void initData() {
        super.initData();

        testB.setText("bbbbbbbbbbbbbb");

/*        list.clear();

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        Map<String, Object> map5 = new HashMap<>();

        map.put("image", "https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg");
        map1.put("image", "https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg");
        map2.put("image", "http://ww1.sinaimg.cn/large/0073sXn7ly1ft82s05kpaj30j50pjq9v.jpg");
        map3.put("image", "https://ww1.sinaimg.cn/large/0065oQSqly1ft5q7ys128j30sg10gnk5.jpg");
        map4.put("image", "https://ww1.sinaimg.cn/large/0065oQSqgy1ft4kqrmb9bj30sg10fdzq.jpg");
        map5.put("image", "http://ww1.sinaimg.cn/large/0065oQSqly1ft3fna1ef9j30s210skgd.jpg");

        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);*/


        adapter = new RecyclerviewAdapter(list, this);
        easyRecy.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                showTipMsg((String) list.get(position).get("image"));
            }
        });


        //自动刷新
        refreshLayout.autoRefresh();    //（非必须）
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）


        //basePresenter.getContent(num, page);

    }

    @Override
    protected void initEvent() {
        super.initEvent();


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //最长刷新时间
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                //刷新时先清空list
                list.clear();
                page = 1;
                basePresenter.getContent(num, page);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //最长加载时间
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

                page++;
                basePresenter.getContent(num, page);

            }
        });


    }

    @OnClick({R.id.test_b})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_b:
                //App.getInstance().exitApp();

                // 发送消息
                EventBus.getDefault().post(new EventBusMsg("我是BActivity的EventBus发送的数据"));
                // 销毁当前Activity
                myFinish();
                break;
        }
    }


    @Override
    public void showContent(List<GankItemBean> gankItemBeen) {
        //testB.setText(gankItemBeen.toString());

        for (int i = 0; i < gankItemBeen.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", gankItemBeen.get(i).getUrl());
            list.add(map);
        }

        adapter.notifyDataSetChanged();

        finishRefresh();

        if (gankItemBeen.size() == 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件    //（非必须）
        }

    }


    //停止刷新和加载
    public void finishRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

}
