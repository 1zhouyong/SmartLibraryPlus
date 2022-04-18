package com.example.smartlibrary.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary.LectureListAdapter;
import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseFragment;
import com.example.smartlibrary.base.BaseMvpFragment;
import com.example.smartlibrary.bean.LectureBean;
import com.example.smartlibrary.contract.LectureMainContract;
import com.example.smartlibrary.presenter.LectureMainPresenter;
import com.example.smartlibrary.widget.NormalTitleBar;

import java.util.List;

import butterknife.BindView;
import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;

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
 * 2022/3/23 : Create LecturesMainFragment.java
 * -----------------------------------------------------------------
 */
public class LecturesMainFragment extends BaseMvpFragment<LectureMainPresenter> implements LectureMainContract.View {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.rec_lecture)
    RecyclerView recLecture;
    private LectureMainPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LectureMainPresenter();
        presenter.attachView(this);

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lecture;
    }

    @Override
    public void getLectureSuccess(List<LectureBean> list) {
        LectureListAdapter adapter = new LectureListAdapter(getActivity(), list);
        recLecture.setLayoutManager(new LinearLayoutManager(getActivity()));
        ScaleInAnimatorAdapter scaleInRecyclerViewAnimationAdapter = new ScaleInAnimatorAdapter(adapter, recLecture);
        recLecture.setAdapter(scaleInRecyclerViewAnimationAdapter);


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
}
