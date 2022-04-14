package com.example.smartlibrary.ui.activity;

import android.os.Bundle;

import com.example.smartlibrary.R;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.contract.ReservationContract;
import com.example.smartlibrary.presenter.ReservationPresenter;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.ShareUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationActivity extends BaseMvpActivity<ReservationPresenter> implements ReservationContract.View {


    private ReservationPresenter presenter;
    private String dateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateType = getIntent().getExtras().getString("date");
        presenter = new ReservationPresenter();
        presenter.attachView(this);
        getSeatByCondition(dateType, "一楼-101");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reservation;
    }

    @Override
    public void initView() {


    }

    private void getSeatByCondition(String dateType, String place) {
        Map<String, String> map = new HashMap<>();
        map.put("date", dateType);
        map.put("place", place);
        String token = ShareUtils.getString(BaseApplication.getAppContext(), "token", "");
        presenter.getSeatList(token, MapToRequestBodyUtil.convertMapToBody(map));
    }


    @Override
    public void success(List<SeatListBean> list) {
        LogUtils.logd("SeatListBean.size == " +list.size());
    }


    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String errMessage) {

    }
}