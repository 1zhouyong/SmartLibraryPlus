package com.example.smartlibrary.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseFragment;
import com.example.smartlibrary.ui.activity.ReservationActivity;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.widget.NormalTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.tv_tomorrow)
    TextView tvTomorrow;
    @BindView(R.id.btn_today)
    Button btnToday;
    @BindView(R.id.btn_tomorrow)
    Button btnTomorrow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView(View view) {
        normalTitleBar.setBackVisibility(false);
        normalTitleBar.setTitleText("预约座位");
        tvToday.setText(PublicTools.getNowDay(0));
        tvTomorrow.setText(PublicTools.getNowDay(1));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_seat;
    }

    @OnClick({R.id.btn_today,R.id.btn_tomorrow})
    public void onViewClicked(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.btn_today:
                bundle.putString("date","1");
                break;
            case R.id.btn_tomorrow:
                bundle.putString("date","2");
                break;
        }
        startActivity(ReservationActivity.class,bundle);
    }


}
