package com.example.smartlibrary.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.LectureBean;
import com.example.smartlibrary.contract.LectureInfoContract;
import com.example.smartlibrary.presenter.LectureInfoPresenter;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.NormalTitleBar;

import java.util.HashMap;

import butterknife.BindView;



public class LectureInfoActivity extends BaseMvpActivity<LectureInfoPresenter> implements LectureInfoContract.View, View.OnClickListener {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.tv_lecture_name)
    TextView lectureName;
    @BindView(R.id.tv_lecture_location)
    TextView lectureLocation;
    @BindView(R.id.tv_lecture_people)
    TextView lecturePeople;
    @BindView(R.id.tv_lecture_teacher)
    TextView lectureTeacher;
    @BindView(R.id.tv_startTime)
    TextView startTime;
    @BindView(R.id.tv_stopTime)
    TextView stopTime;
    @BindView(R.id.btn_bookLecture)
    Button btnBookLecture;
    private LectureBean lectureBean;
    private LectureInfoPresenter presenter;
    private String token;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lecture_info;
    }

    @Override
    public void initView() {
        token = ShareUtils.getString(this, "token", "");
        lectureBean = (LectureBean) getIntent().getSerializableExtra("LectureBean");
        userId = getIntent().getStringExtra("userId");
        if (userId == null){
            btnBookLecture.setVisibility(View.GONE);
        }
        lectureName.setText(lectureBean.getName());
        lectureLocation.setText(lectureBean.getLocation());
        lectureTeacher.setText(lectureBean.getTeacher());
        lecturePeople.setText(lectureBean.getOrderNumber()+"/"+lectureBean.getSumNumber());
        startTime.setText(PublicTools.getDate(lectureBean.getBeginTime()));
        stopTime.setText(PublicTools.getDate(lectureBean.getEndTime()));
        presenter = new LectureInfoPresenter();
        presenter.attachView(this);
        btnBookLecture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("lectureId",lectureBean.getId()+"");
        presenter.submitOrderLecture(token, MapToRequestBodyUtil.convertMapToBody(map));
    }

    @Override
    public void orderSuccess(boolean isSuccess) {
        if (isSuccess){
            showLongToast("预约成功，可前往我的预约中查看");
        }
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