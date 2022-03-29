package com.example.smartlibrary.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseFragment;
import com.example.smartlibrary.widget.NormalTitleBar;

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
 * 2022/3/23 : Create SeatsMainFragment.java
 * -----------------------------------------------------------------
 */
public class SeatsMainFragment extends BaseFragment {


    @BindView(R.id.ntb)
    NormalTitleBar normalTitleBar;


    @Override
    protected void initView(View view) {
        normalTitleBar.setBackVisibility(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_seat;
    }
}
