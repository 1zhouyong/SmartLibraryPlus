package com.example.smartlibrary.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.SeatPageAdapter;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.contract.ReservationContract;
import com.example.smartlibrary.presenter.ReservationPresenter;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.CustomSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ReservationActivity extends BaseMvpActivity<ReservationPresenter> implements ReservationContract.View,
        CustomSpinner.OnSpinnerItemClickListener, AdapterView.OnItemClickListener, View.OnClickListener {


    private ReservationPresenter presenter;
    private String dateType;
    private List<String> mDataResouce = null;


    @BindView(R.id.customSpinner)
    CustomSpinner customSpinner;
    @BindView(R.id.gridView_seat)
    GridView gridViewSeat;
    @BindView(R.id.btn_reservation)
    Button btnReservation;
    private SeatPageAdapter adapter;
    private int currentClickItem = -1;
    private int currentClassItem = 0;
    private String classroom[];
    private List<String> seatStatusList = new ArrayList<>();
    private List<SeatListBean> seatList = new ArrayList<>();
    private String userId;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateType = getIntent().getExtras().getString("date");
        userId = getIntent().getExtras().getString("userId");
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
        gridViewSeat.setOnItemClickListener(this);
        initDataResouce();
        initCustomSpinner();
        adapter = new SeatPageAdapter(this, seatStatusList);
        gridViewSeat.setAdapter(adapter);
        btnReservation.setOnClickListener(this);
    }

    private void initDataResouce() {
        classroom = getResources().getStringArray(R.array.classroom);
        mDataResouce = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            mDataResouce.add(classroom[i]);
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
        map.put("date", PublicTools.getNowDay1(Integer.parseInt(dateType)));
        map.put("place", place);
        token = ShareUtils.getString(BaseApplication.getAppContext(), "token", "");
        presenter.getSeatList(token, MapToRequestBodyUtil.convertMapToBody(map));
    }


    @Override
    public void success(List<SeatListBean> list) {
        seatList.clear();
        seatList.addAll(list);
        LogUtils.logd("place === " + list.get(0).getPlace());
        resetClick();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void orderSeatSuccess(Boolean isSuccess) {
        if (isSuccess){
            showLongToast("预约成功");
            LogUtils.logd("currentClickItem == " + currentClickItem);
            seatList.get(currentClickItem % 30).setStatus("1");
        }else {
            showLongToast("预约失败");
        }

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
        currentClassItem = position * 30;
        getSeatByCondition(dateType, classroom[position]);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        btnReservation.setEnabled(true);
        LogUtils.logd("onItemClick === "+ seatStatusList.get(i).equals("1"));
        if (!seatStatusList.get(i).equals("1")){
            resetClick();
            currentClickItem = i+1 + currentClassItem;
            LogUtils.logd("currentClickItem -- "+ currentClickItem);
            seatStatusList.set(i,"1");
            adapter.notifyDataSetChanged();
        }
    }

    private void resetClick(){
        seatStatusList.clear();
        for (int j = 0; j < seatList.size(); j++) {
            seatStatusList.add(seatList.get(j).getStatus());
        }
    }

    @Override
    public void onClick(View view) {
        HashMap<String, String> map = new HashMap<>();
        map.put("date", PublicTools.getNowDay1(Integer.valueOf(dateType.trim())));
        map.put("id",String.valueOf((currentClickItem)));
        map.put("userId",userId);

        presenter.order(token,MapToRequestBodyUtil.convertMapToBody(map));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}