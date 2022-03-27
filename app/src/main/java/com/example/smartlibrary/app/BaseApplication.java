package com.example.smartlibrary.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.example.smartlibrary.BuildConfig;
import com.example.smartlibrary.adapter.channel.ChannelBean;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

import imageloader.libin.com.images.loader.ImageLoader;


/**
 * 图书类别：
 * <p>
 * 哲学、宗教、经济、文化、科学、教育、体育、语言、文字、文学
 * 艺术、历史、地理、医药、卫生、农业、工业、交通、航空、环境
 */


/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
        //初始化首页频道
        initChannel();
        //初始化token
        ShareUtils.putString(baseApplication, "token", "");
        // 初始化Imageloader
        ImageLoader.init(getApplicationContext());

        PublicTools.changeImageUrl("D:\\\\tmp\\\\libary\\\\web\\\\src\\\\main\\\\resources\\\\static\\\\bookpic\\\\1.png");
    }

    private void initChannel() {
        List<ChannelBean> myChannelBeansList = new ArrayList<>();
        myChannelBeansList.add(new ChannelBean("哲学", 2));
        myChannelBeansList.add(new ChannelBean("宗教", 2));
        myChannelBeansList.add(new ChannelBean("经济", 2));
        myChannelBeansList.add(new ChannelBean("文化", 2));
        myChannelBeansList.add(new ChannelBean("科学", 2));


        List<ChannelBean> othersChannelBeansList = new ArrayList<>();
        othersChannelBeansList.add(new ChannelBean("教育", 3));
        othersChannelBeansList.add(new ChannelBean("体育", 3));
        othersChannelBeansList.add(new ChannelBean("语言", 3));
        othersChannelBeansList.add(new ChannelBean("医药", 3));
        othersChannelBeansList.add(new ChannelBean("卫生", 3));

        othersChannelBeansList.add(new ChannelBean("艺术", 3));
        othersChannelBeansList.add(new ChannelBean("历史", 3));
        othersChannelBeansList.add(new ChannelBean("地理", 3));
        othersChannelBeansList.add(new ChannelBean("文学", 3));
        othersChannelBeansList.add(new ChannelBean("科学", 3));

        othersChannelBeansList.add(new ChannelBean("农业", 3));
        othersChannelBeansList.add(new ChannelBean("工业", 3));
        othersChannelBeansList.add(new ChannelBean("交通", 3));
        othersChannelBeansList.add(new ChannelBean("航空", 3));
        othersChannelBeansList.add(new ChannelBean("环境", 3));

        if (ShareUtils.getList(baseApplication, "MyChannel") == null) {
            ShareUtils.putList(baseApplication, "MyChannel", myChannelBeansList);
            ShareUtils.putList(baseApplication, "OthersChannel", othersChannelBeansList);
        }
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    //为了防止oom,加入如下代码，清理内存
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageLoader.trimMemory(level);
    }

    //为了防止oom,加入如下代码，清理内存
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.clearAllMemoryCaches();
    }


}
