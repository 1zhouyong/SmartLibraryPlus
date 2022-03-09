package com.example.smartlibrary.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2011-2022, by your Signway, All rights reserved.
 * -----------------------------------------------------------------
 *
 * ProjectName: SmartLibrary
 *
 * Author: ZY
 *
 * Email: yong.zhou@signway.cn
 *
 * Description:
 *
 * -----------------------------------------------------------------
 * 2022/3/9 : Create MyPagerAdapter.java
 * -----------------------------------------------------------------
 */
public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<ImageView> imageViews;


    public MyPagerAdapter(ArrayList<ImageView> imageViews){
        this.imageViews = imageViews;
    }


    @Override
    public int getCount() {
        return imageViews.size();
    }

    /**
     * @param container viewPager容器
     * @param position  创建视图对应的位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = imageViews.get(position);
        container.addView(imageView);
        return imageView;
    }

    /**
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
