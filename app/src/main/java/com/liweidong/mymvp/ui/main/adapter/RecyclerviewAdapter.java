package com.liweidong.mymvp.ui.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.liweidong.mymvp.R;
import com.liweidong.mymvp.app.App;
import com.liweidong.mymvp.component.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Author:李炜东
 * Date:2018/9/7.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHloder> {
    private List<Map<String, Object>> list;
    private Context context;

    public RecyclerviewAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHloder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_b_con, null);
        return new MyViewHloder(view);
    }

    /**
     * 在StaggeredGridLayoutManager瀑布流中,当需要依据图片实际相对高度,不断动态设置ImageView的LayoutParams时,
     * 会导致快速滑动状态下产生重新排列,重写getItemViewType并设置StaggeredGridLayoutManager.GAP_HANDLING_NONE解决了这个问题，原因目前未知
     * https://github.com/oxoooo/mr-mantou-android/blob/master/app/src/main/java/ooo/oxo/mr/MainAdapter.java
     *
     * @param position
     * @return
     */
    @Override
/*       public int getItemViewType(int position) {
        return Math.round((float) App.SCREEN_WIDTH / (float) mList.get(position).getHeight() * 10f);
    }*/

    public void onBindViewHolder(final MyViewHloder holder, final int position) {

        String url = (String) list.get(position).get("image");

        String height = (String) list.get(position).get("height");


        //Log.i("image", url);

        //holder.iv_girl.setBackgroundResource(R.drawable.def);
        //https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg



/*
后台在返回图片的时候返回高度，可以获得image的LayoutParams重新设置高度

如果没返回，则只能在item布局中直接写一个高度值

为了实现瀑布流也可以生成一个高度随机数，获得image的LayoutParams重新设置高度(但是因为高度不确定，会导致出现空白问题)
 */


/*        //图片宽
        int width = App.SCREEN_WIDTH/3;
        //图片高
        int height = (int) (width*(100)*(position+1)/100);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.iv_girl.getLayoutParams();
        layoutParams.height = height;
        holder.iv_girl.setLayoutParams(layoutParams);*/

/*        //存在记录的高度时先Layout再异步加载图片
        if (mList.get(holder.getAdapterPosition()).getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = holder.ivGirl.getLayoutParams();
            layoutParams.height = mList.get(holder.getAdapterPosition()).getHeight();
        }*/

        //产生一个2以内的整数：int x=(int)(Math.random()*100);
        //0 1 2
        int x = (int) (Math.random() * 2);


        //图片宽
        int width = App.SCREEN_WIDTH / 3;
        int img_height = 0;
        //图片高
        //高度设为 宽加上宽的0.1-0.3倍的
        //img_height = (int) (width+width*(x+1)/10);

        img_height = (int) (width * 2);


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.iv_girl.getLayoutParams();
        layoutParams.height = img_height;
        holder.iv_girl.setLayoutParams(layoutParams);

        ImageLoader.showImageDef(context, url, holder.iv_girl);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            //Log.i("image_list.size", list.size() + "");
            return list.size();
        }
    }

    class MyViewHloder extends RecyclerView.ViewHolder {
        ImageView iv_girl;

        public MyViewHloder(View itemView) {
            super(itemView);
            iv_girl = (ImageView) itemView.findViewById(R.id.iv_girl);
        }
    }


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

