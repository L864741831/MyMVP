package com.liweidong.mymvp.ui.room.adapter;

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
import com.liweidong.mymvp.model.bean.PhoneBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Author:李炜东
 * Date:2018/9/7.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.MyViewHloder> {
    private List<PhoneBean> list;
    private Context context;

    public PhoneListAdapter(List<PhoneBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHloder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phone_text, null);
        return new MyViewHloder(view);
    }

    public void onBindViewHolder(final MyViewHloder holder, final int position) {

        //String url = (String) list.get(position).get("image");

        holder.phone_number_text.setText(list.get(position).getId() + "\n" + list.get(position).getName() + "\n" + list.get(position).getPhone() + "\n" + getFormatDate(list.get(position).getDate()) + "\n");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(list.get(position));
            }
        });
    }

    private String getFormatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            //Log.i("11111",list.size()+"");
            return list.size();
        }
    }

    class MyViewHloder extends RecyclerView.ViewHolder {
        TextView phone_number_text;

        public MyViewHloder(View itemView) {
            super(itemView);
            phone_number_text = (TextView) itemView.findViewById(R.id.phone_number_text);
        }
    }

    private OnItemClickListener onItemClickListener;

    //定义接口，绑定后（setOnItemClickListener）让activity实现该接口
    public interface OnItemClickListener {
        void OnItemClick(PhoneBean phoneBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}

