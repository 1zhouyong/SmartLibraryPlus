package com.example.smartlibrary.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.MySeatAdapter;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.MySeatBean;
import com.example.smartlibrary.contract.MySeatContract;
import com.example.smartlibrary.presenter.MySeatPresenter;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MySeatInfoActivity extends BaseMvpActivity<MySeatPresenter> implements MySeatContract.View, MySeatAdapter.CancelCallBack {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.rec_my_seat)
    RecyclerView recMySeat;
    private String token;
    private String userId;
    private MySeatPresenter presenter;
    private List<MySeatBean> mySeatList = new ArrayList<>();
    private String name;
    private String studyId;
    private MySeatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_seat_info;
    }

    @Override
    public void initView() {
        ntb.setTitleText("我的座位");
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        token = ShareUtils.getString(BaseApplication.getAppContext(), "token", "");
        userId = getIntent().getStringExtra("userId");
        name = getIntent().getStringExtra("name");
        studyId = getIntent().getStringExtra("studyId");
        adapter = new MySeatAdapter(this,mySeatList);
        recMySeat.setLayoutManager(new LinearLayoutManager(this));
        recMySeat.setAdapter(adapter);

        presenter = new MySeatPresenter();
        presenter.attachView(this);
        submit(0);
        submit(1);
        adapter.setCancelCallBack(this);

    }

    private void submit(int day) {
        HashMap<String, String> map = new HashMap<>();
        map.put("day", PublicTools.getNowDay1(day));
        map.put("userId", userId);
        presenter.submit(token,MapToRequestBodyUtil.convertMapToBody(map),PublicTools.getNowDay1(day));
    }


    @Override
    public void getMySeatSuccess(MySeatBean bean,String date) {
        if (bean != null){
            bean.setDate(date);
            mySeatList.add(bean);
            adapter.setName(name);
            adapter.setStudyId(studyId);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancelSucees(int postion) {
        showLongToast("取消成功");
        mySeatList.remove(postion);
        adapter.notifyDataSetChanged();
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
    public void cancel(int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("date", mySeatList.get(position).getDate()+"");
        map.put("id",mySeatList.get(position).getId()+"");
        map.put("userId",userId);
        LogUtils.logd("date == " + mySeatList.get(position).getDate() + "id == "+mySeatList.get(position).getId());
        presenter.cancelSeat( token , MapToRequestBodyUtil.convertMapToBody(map),position);
    }
}