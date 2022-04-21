package com.example.smartlibrary.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.MySeatAdapter;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.MySeatBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MySeatContract;
import com.example.smartlibrary.net.RetrofitClient;
import com.example.smartlibrary.presenter.MySeatPresenter;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        adapter = new MySeatAdapter(this,mySeatList);
        recMySeat.setLayoutManager(new LinearLayoutManager(this));
        recMySeat.setAdapter(adapter);
        token = ShareUtils.getString(BaseApplication.getAppContext(), "token", "");
        userId = getIntent().getStringExtra("userId");
        name = getIntent().getStringExtra("name");
        studyId = getIntent().getStringExtra("studyId");
        presenter = new MySeatPresenter();
        presenter.attachView(this);
        submit("0");
        submit("1");
        adapter.setCancelCallBack(this);

    }

    private void submit(String day) {
        HashMap<String, String> map = new HashMap<>();
        map.put("day",day);
        map.put("userId", userId);
        presenter.submit(token,MapToRequestBodyUtil.convertMapToBody(map));
    }


    @Override
    public void getMySeatSuccess(MySeatBean bean) {
        mySeatList.add(bean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void cancelSucees(int postion) {
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
        map.put("date", (String) mySeatList.get(position).getDate());
        map.put("id",mySeatList.get(position).getId()+"");
        map.put("userId",userId);
        presenter.cancelSeat(token,MapToRequestBodyUtil.convertMapToBody(map),position);
    }
}