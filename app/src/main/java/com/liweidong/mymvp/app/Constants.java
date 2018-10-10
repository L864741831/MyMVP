package com.liweidong.mymvp.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    //================= KEY ====================


    public static final String FILE_PROVIDER_AUTHORITY="com.codeest.geeknews.fileprovider";


    //================= PATH ====================

    //File.separator作为文件分隔符，在不同平台下兼容，
    // file.separator这个代表系统目录中的间隔符，说白了就是斜线，不过有时候需要双线，有时候是单线，你用这个静态变量就解决兼容问题了。
    //缓存目录下data文件夹
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    //缓存目录下data文件夹中NetCache文件
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

}
