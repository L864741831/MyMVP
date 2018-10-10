package com.liweidong.mymvp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


import com.liweidong.mymvp.R;
import com.liweidong.mymvp.di.component.AppComponent;
import com.liweidong.mymvp.di.component.DaggerAppComponent;
import com.liweidong.mymvp.di.module.AppModule;
import com.liweidong.mymvp.di.module.HttpModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.HashSet;
import java.util.Set;

/**
 * Author:李炜东
 * Date:2018/8/31.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class App extends Application {

    private static App instance;

    //用来保存启动的活动集合
    private Set<Activity> allActivities;

    private static AppComponent appComponent;

    //屏幕宽高
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static App getInstance() {
        return instance;
    }

    private void setInstance(App instance) {
        App.instance = instance;
    }

    public void onCreate() {
        super.onCreate();

        //初始化屏幕宽高
        getScreenSize();

        setInstance(this);
    }

    //将活动添加到allActivities集合
    public void registerActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    //将活动移出allActivities集合
    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /*
    退出App时finish掉全部activity
    对类内allActivities集合加锁，保证同一时间只能一个线程操作allActivities集合，只有方法块执行结束才释放allActivities的锁
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null && !act.isFinishing())
                        act.finish();
                }
            }
        }
        /*
finish是Activity的类，仅仅针对Activity，当调用finish()时，只是将活动推向后台，并没有立即释放内存，活动的资源并没有被清理；
当调用System.exit(0)时，杀死了整个进程，这时候活动所占的资源也会被释放。

其实android的机制决定了用户无法完全退出应用，当你的application最长时间没有被用过的时候，android自身会决定将application关闭了。

android.os.Process.killProcess(android.os.Process.myPid());

Process.killProcess 最终是调用 linux API kill() 发送 SIGKILL 信号。
了解 Linux 编程的都知道，进行收到这个信息都会立即结束进程，Android 下不同的是 ActivityManager 一直监听者进程状态。
如果发现进程被kill，会立即重启进行，并重启之前状态对应的Activity、Service、ContentProvider等。
这就是为什么我们调用Process.killProcess后，发现程序是重启了，而不是被kill了
         */

        /*
        在配置了
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
         的主活动页面调用下面关闭方法可以退出应用，

         在其他活动界面调用下面方法，应用被杀死后会重启到主活动。
         */
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 获取AppComponent.
     *
     * @return AppComponent
     *
     * 准备将AppModule与HttpModule工厂类中的对象注入到指定位置
     */
    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule())
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }




    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }



    //全局设置Smart的刷新加载样式与颜色
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorAccent, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


}
