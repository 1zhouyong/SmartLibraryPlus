package com.example.smartlibrary.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary.adapter.LectureListAdapter;
import com.example.smartlibrary.R;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpFragment;
import com.example.smartlibrary.bean.LectureBean;
import com.example.smartlibrary.contract.LectureMainContract;
import com.example.smartlibrary.presenter.LectureMainPresenter;
import com.example.smartlibrary.ui.activity.LectureInfoActivity;
import com.example.smartlibrary.ui.activity.MainActivity;
import com.example.smartlibrary.utils.ShareUtils;
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
public class LecturesMainFragment extends BaseMvpFragment<LectureMainPresenter> implements LectureMainContract.View, LectureListAdapter.LectureListClick {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.rec_lecture)
    RecyclerView recLecture;
    private LectureMainPresenter presenter;
    private MainActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LectureMainPresenter();
        presenter.attachView(this);
        presenter.selectLecture(ShareUtils.getString(BaseApplication.getAppContext(), "token"));
        activity = (MainActivity) getActivity();

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
        adapter.setLectureListClick(this);
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
    public void onLectureClick(LectureBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("LectureBean",bean);
        bundle.putString("userId",activity.infoBean.getId()+"");
        startActivity(LectureInfoActivity.class,bundle);
    }
}
