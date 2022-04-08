package com.example.smartlibrary.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.BookPageAdapter;
import com.example.smartlibrary.base.BaseMvpFragment;
import com.example.smartlibrary.bean.BookTypeBean;
import com.example.smartlibrary.contract.BookMainContract;
import com.example.smartlibrary.presenter.BookMainPresenter;
import com.example.smartlibrary.ui.activity.BookInfoActivity;
import com.example.smartlibrary.utils.LogUtils;
import com.flyco.tablayout.CommonTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
 * 2022/3/25 : Create BookFragment.java
 * -----------------------------------------------------------------
 */
public class BookPageFragment extends BaseMvpFragment<BookMainPresenter> implements BookMainContract.View, View.OnTouchListener {


    @BindView(R.id.gridView_book_channel)
    GridView gridView;
    @BindView(R.id.ll)
    LinearLayout ll;

    private String bookType;
    private BookMainPresenter presenter;
    private List<BookTypeBean> bookTypeBeans = new ArrayList<>();
    private float curPosX;
    private float curPosY;
    private float posX;
    private float posY;
    private CommonTabLayout mainTab;
    private int measuredHeight;

    @Override
    protected void initView(View view) {
        if (getArguments() != null) {
            bookType = getArguments().getString("bookType");
            presenter = new BookMainPresenter();
            presenter.attachView(this);
            presenter.selectBook(bookType);
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("book",bookTypeBeans.get(i));
                startActivity(BookInfoActivity.class, bundle);
            }
        });
        gridView.setOnTouchListener(this);
        mainTab = getActivity().findViewById(R.id.tab_layout);
        measuredHeight = mainTab.getMeasuredHeight();

    }
    /**
     * 菜单显示隐藏动画
     * @param showOrHide
     */
    private void startAnimation(boolean showOrHide){
        final ViewGroup.LayoutParams layoutParams = mainTab.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if(!showOrHide){
            valueAnimator = ValueAnimator.ofInt(measuredHeight, 0);
            alpha = ObjectAnimator.ofFloat(mainTab, "alpha", 1, 0);
        }else{
            valueAnimator = ValueAnimator.ofInt(0, measuredHeight);
            alpha = ObjectAnimator.ofFloat(mainTab, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height= (int) valueAnimator.getAnimatedValue();
                mainTab.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator,alpha);
        animatorSet.start();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_channel;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String errMessage) {

    }

    @Override
    public void onSuccess(List<BookTypeBean> bean) {
        bookTypeBeans.addAll(bean);
        BookPageAdapter bookPageAdapter = new BookPageAdapter(getActivity(), bean);
        gridView.setAdapter(bookPageAdapter);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        LogUtils.logd("onTouch");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                posX = event.getX();
                posY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curPosX = event.getX();
                curPosY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if ((curPosX - posX > 0) && (Math.abs(curPosX - posX) > 25)){
                }
                else if ((curPosX - posX < 0) && (Math.abs(curPosX - posX) > 25)){
                }
                if ((curPosY - posY > 0) && (Math.abs(curPosY - posY) > 25)){
                    Log.v("MainActivity","向上滑动");
                    LogUtils.logd("向上滑动curPosY - posY === " + (curPosY - posY)
                    +"----Math.abs(curPosY - posY) === " + Math.abs(curPosY - posY));
                    startAnimation(true);
                }
                else if ((curPosY - posY < 0) && (Math.abs(curPosY - posY) > 25)){
                    LogUtils.logd("向下滑动curPosY - posY === " + (curPosY - posY)
                            +"----Math.abs(curPosY - posY) === " + Math.abs(curPosY - posY));
                    Log.v("MainActivity","向下滑动");
                    startAnimation(false);
                }
                break;
        }
        return false;
    }

}
