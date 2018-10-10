package com.liweidong.mymvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liweidong.mymvp.R;

/**
 * Author:李炜东
 * Date:2018/9/7.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class EasyRecyclerView extends FrameLayout {

    //列表方向布局枚举
    private ListDirection listDirection;

    //网格或瀑布流每行/列数量
    private int listNum;

    public RecyclerView rv;
    public TextView tv;


    //为空提示语是否显示
    private boolean isShowEmptyText;
    //列表为空提示语
    private String emptyText;

    public EasyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_empty_show, null);
        rv = v.findViewById(R.id.easy_rv);
        /*
        RecyclerView默认是setNestedScrollingEnabled(true),是支持嵌套滚动的,
        也就是说当它嵌套在NestedScrollView中时,
        默认会随着NestedScrollView滚动而滚动,放弃了自己的滚动.
        所以给我们的感觉就是滞留、卡顿.主动将该值置false可以有效解决该问题.
         */
        rv.setNestedScrollingEnabled(false);
        /*
        使用NestedScrollView嵌套RecyclerView时,每次打开界面都是定位在RecyclerView在屏幕顶端,列表上面的布局都被顶上去了

        查看RecyclerView的源码发现，它会在构造方法中调用setFocusableInTouchMode(true),
        所以抢到焦点后一定会定位到第一行的位置突出RecyclerView的显示
         */
        rv.setFocusable(false);

        //ListView、RecyclerView自动跳到页面顶部或者中间的问题
        rv.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        // 等同RecyclerView <!-- android:overScrollMode="never"滑动到边缘无效果 -->
        rv.setOverScrollMode(OVER_SCROLL_NEVER);

        tv = (TextView) v.findViewById(R.id.easy_tv);

        this.addView(v);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasyRecyclerView);

        emptyText=typedArray.getString(R.styleable.EasyRecyclerView_emptyText);
        isShowEmptyText=typedArray.getBoolean(R.styleable.EasyRecyclerView_isShowEmptyText,false); //默认不显示空提示

        listDirection = ListDirection.fromStep(typedArray.getInt(R.styleable.EasyRecyclerView_listDirection, 0));
        listNum = typedArray.getInt(R.styleable.EasyRecyclerView_listNum, 1);

        if (listDirection == ListDirection.Linear_VERTICAL) {
            LinearLayoutManager lm = new LinearLayoutManager(context);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(lm);
        } else if (listDirection == ListDirection.Linear_HORIZONTAL) {
            LinearLayoutManager lm = new LinearLayoutManager(context);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv.setLayoutManager(lm);
        } else if (listDirection == ListDirection.Grid_VERTICAL) {
            GridLayoutManager gm = new GridLayoutManager(context, listNum);
            gm.setOrientation(GridLayoutManager.VERTICAL);
            rv.setLayoutManager(gm);
        } else if (listDirection == ListDirection.Grid_HORIZONTAL) {
            GridLayoutManager gm = new GridLayoutManager(context, listNum);
            gm.setOrientation(GridLayoutManager.HORIZONTAL);
            rv.setLayoutManager(gm);
        } else if (listDirection == ListDirection.Staggered_VERTICAL) {
            StaggeredGridLayoutManager sm = new StaggeredGridLayoutManager(listNum, StaggeredGridLayoutManager.VERTICAL);
            //在设置layoutManager的时候，多加一句代码防止item位置交换
            // layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE)
            sm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            rv.setLayoutManager(sm);
        } else if (listDirection == ListDirection.Staggered_HORIZONTAL) {
            StaggeredGridLayoutManager sm = new StaggeredGridLayoutManager(listNum, StaggeredGridLayoutManager.HORIZONTAL);
            //在设置layoutManager的时候，多加一句代码防止item位置交换
            // layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE)
            sm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            rv.setLayoutManager(sm);
        } else {
            //默认线性竖直
            LinearLayoutManager lm = new LinearLayoutManager(context);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(lm);
        }

        tv.setText(emptyText);

    }

    public RecyclerView.Adapter<?> getAdapter() {
        return rv.getAdapter();
    }

    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        rv.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                RecyclerView.Adapter adapter=getAdapter();
                //列表为空且显示为空提示
                if (adapter.getItemCount()==0&&isShowEmptyText){
                    rv.setVisibility(GONE);
                    tv.setVisibility(VISIBLE);
                }else {
                    rv.setVisibility(VISIBLE);
                    tv.setVisibility(GONE);
                }


            }
        });
    }




    //添加滑动监听
/*    public void addOnScrollListener(RecyclerView.OnScrollListener OnScrollListener){
        rv.addOnScrollListener(OnScrollListener);
    }*/

    /*
    列表方向布局枚举
     */
    private enum ListDirection {
        //线性竖直,线性水平 网格竖直，网格水平 交错的竖直，交错的水平
        Linear_VERTICAL(0), Linear_HORIZONTAL(1), Grid_VERTICAL(2), Grid_HORIZONTAL(3), Staggered_VERTICAL(4), Staggered_HORIZONTAL(5);

        int step;

        ListDirection(int step) {
            this.step = step;
        }

        public static ListDirection fromStep(int step) {
            for (ListDirection f : values()) {
                if (f.step == step) {
                    return f;
                }
            }
            throw new IllegalArgumentException();
        }


    }
}
