package com.example.smartlibrary.ui.activity;

import android.os.Bundle;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.MyLectureAdapter;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.MyLectureBean;
import com.example.smartlibrary.contract.MyLectureContract;
import com.example.smartlibrary.presenter.MyLecturePresenter;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.NormalTitleBar;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MyLectureActivity extends BaseMvpActivity<MyLecturePresenter> implements MyLectureContract.View {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.lv_myLecture)
    SwipeMenuListView lvMyLecture;
    private String userId;
    private String token;
    private MyLecturePresenter presenter;
    private MyLectureAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_lecture;
    }

    @Override
    public void initView() {
        userId = getIntent().getStringExtra("userId");
        token = ShareUtils.getString(BaseApplication.getAppContext(), "token");
        presenter = new MyLecturePresenter();
        presenter.attachView(this);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        presenter.submitMyLecture(token, MapToRequestBodyUtil.convertMapToBody(map));
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
    public void getLectureListSuccess(List<MyLectureBean> beans) {
        if (beans.size() == 0 || beans == null){

        }else {
            adapter = new MyLectureAdapter(this, beans);
            lvMyLecture.setAdapter(adapter);
        }

    }
}