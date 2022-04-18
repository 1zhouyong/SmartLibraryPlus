package com.example.smartlibrary.adapter.animator;

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
 * 2022/4/18 : Create ScaleInAnimatorAdapter.java
 * -----------------------------------------------------------------
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * An implementation of the AnimatorAdapter class which applies a
 * scale-animation to views.
 *
 * @Author Gabriele Mariotti
 */
public class ScaleInAnimatorAdapter<T extends RecyclerView.ViewHolder> extends AnimatorAdapter<T> {

    private static final float DEFAULT_SCALE_FROM = 0.6f;

    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";

    private final float mScaleFrom;

    public ScaleInAnimatorAdapter( final RecyclerView.Adapter<T> adapter,
                                  RecyclerView recyclerView) {
        this(adapter, recyclerView, DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimatorAdapter( final RecyclerView.Adapter<T> adapter,
                                  RecyclerView recyclerView,
                                  final float scaleFrom) {
        super(adapter, recyclerView);
        mScaleFrom = scaleFrom;
    }


    @Override
    public Animator[] getAnimators( View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, SCALE_X, mScaleFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, SCALE_Y, mScaleFrom, 1f);
        return new ObjectAnimator[]{scaleX, scaleY};
    }
}