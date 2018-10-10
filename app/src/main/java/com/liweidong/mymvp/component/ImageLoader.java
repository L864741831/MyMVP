package com.liweidong.mymvp.component;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liweidong.mymvp.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author:李炜东
 * Date:2018/9/6.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class ImageLoader {

    //crossFade淡入淡出
    /*
    crossFade()方法有另外一个特征：.crossFade(int duration),如果你想要减慢（或加快）动画，
    随便传入一个毫秒级的时间进去感受一下。默认的动画时间是300毫秒。
     */
    public static void showImage(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public static void showImageDef(Context context, String url, ImageView photo) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.def)
                .fallback(R.drawable.def)
                .into(photo);
    }

    //图片上边两个圆角、第二个为角度=====new RoundedCornersTransformation(context, 10, 0, RoundedCornersTransformation.CornerType.TOP)
    public static void showImageTop(Context context, String url, ImageView photo) {
        //上面两个圆角，下面两个直角

        //如果是四周是圆角则RoundedCornersTransformation.CornerType.ALL
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.def)
                .fallback(R.drawable.def)
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(photo);

    }

    //图片下边两个圆角
    public static void showImageBottom(Context context, String url, ImageView photo) {
        //下面两个圆角，上面两个直角
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.def)
                .fallback(R.drawable.def)
                //                .bitmapTransform(new GrayscaleTransformation(this))//带灰色蒙层
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0,
                        RoundedCornersTransformation.CornerType.BOTTOM))
                .into(photo);
    }


    //高斯模糊
    public static void showImageBlur(Context context, String url, ImageView photo,int radius) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.def)
                .fallback(R.drawable.def)
                //radius取值1-25,值越大图片越模糊
                .bitmapTransform(new BlurTransformation(context, radius))
                .into(photo);
    }


    /*
    with() 传入对象 Context,Activity,Fragment，Glide会与当前传入值的生命周期一致。
load() 对象: String(文件路径，网络地址)，File(文件资源)，Integer(资源id)。
asGif() 表示的gif动画，asBitmap：表示静态图。
diskCacheStrategy() 表示磁盘缓存策略。
1.DiskCacheStrategy.RESULT:展示小大的图片缓存
2.DiskCacheStrategy.ALL; 展示在控件中大小图片尺寸和原图都会缓存
3.DiskCacheStrategy.NONE：不设置缓存
4.DiskCacheStrategy.SOURCE：原图缓存
override() 设置图片的width，height
placeholder() 设置占位图
error() 设置失败图
thumbnail() 缩略图显示传入值(0-1f)
crossFade() 显示动画-淡入淡出
transform() 设置图片圆角或圆形显示(继承 BitmapTransformation)这都是很常用的哦
centerCrop() 图片显示类型 fitCenter();
into() 显示到ImageView上

     */
}
