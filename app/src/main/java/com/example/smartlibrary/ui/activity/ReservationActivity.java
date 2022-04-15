package com.example.smartlibrary.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.contract.ReservationContract;
import com.example.smartlibrary.presenter.ReservationPresenter;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.CustomSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ReservationActivity extends BaseMvpActivity<ReservationPresenter> implements ReservationContract.View, CustomSpinner.OnSpinnerItemClickListener {


    private ReservationPresenter presenter;
    private String dateType;
    private List<String> mDataResouce = null;


    @BindView(R.id.customSpinner)
    CustomSpinner customSpinner;
    @BindView(R.id.gridView_seat)
    GridView gridViewSeat;
    @BindView(R.id.btn_reservation)
    Button btnReservation;

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
        initDataResouce();
        initCustomSpinner();

    }

    private void initDataResouce() {
        mDataResouce = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDataResouce.add(getResources().getStringArray(R.array.classroom)[i]);
        }
    }

    private void initCustomSpinner() {
        customSpinner.initData(mDataResouce);
        customSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSpinner.showSpinner();
            }
        });
        customSpinner.setOnSpinnerItemClickListener(this);
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

    @Override
    public void OnSpinnerItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.logd("position === " + position);
    }
}