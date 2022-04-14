package com.example.smartlibrary.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentTransaction;

import com.example.smartlibrary.R;
import com.example.smartlibrary.app.AppConstant;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.UseInfoBean;
import com.example.smartlibrary.contract.MainContract;
import com.example.smartlibrary.entity.TabEntity;
import com.example.smartlibrary.presenter.MainPresenter;
import com.example.smartlibrary.ui.fragment.BooksMainFragment;
import com.example.smartlibrary.ui.fragment.LecturesMainFragment;
import com.example.smartlibrary.ui.fragment.MyMainFragment;
import com.example.smartlibrary.ui.fragment.SeatsMainFragment;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.ShareUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @BindView(R.id.rl)
    RelativeLayout rl;
    private String[] mTitles = {"图书馆", "订座","讲座","我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.tab_book_normal,
            R.mipmap.tab_seat_normal,
            R.mipmap.tab_lecture_normal,
            R.mipmap.tab_my_normal
    };
    private int[] mIconSelectIds = {
            R.mipmap.tab_book_press,
            R.mipmap.tab_seat_press,
            R.mipmap.tab_lecture_press,
            R.mipmap.tab_my_press
    };
    private BooksMainFragment booksMainFragment;
    private SeatsMainFragment seatsMainFragment;
    private LecturesMainFragment lecturesMainFragment;
    private MyMainFragment myMainFragment;
    private static int tabLayoutHeight;
    public UseInfoBean.UserBean infoBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0,0);
        tabLayoutHeight=tabLayout.getMeasuredHeight();
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        // 初始化菜单
        initTab();



    }



    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            booksMainFragment = (BooksMainFragment) getSupportFragmentManager().findFragmentByTag("booksMainFragment");
            seatsMainFragment = (SeatsMainFragment) getSupportFragmentManager().findFragmentByTag("seatsMainFragment");
            lecturesMainFragment = (LecturesMainFragment) getSupportFragmentManager().findFragmentByTag("lecturesMainFragment");
            myMainFragment = (MyMainFragment) getSupportFragmentManager().findFragmentByTag("myMainFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            booksMainFragment = new BooksMainFragment();
            seatsMainFragment = new SeatsMainFragment();
            lecturesMainFragment = new LecturesMainFragment();
            myMainFragment = new MyMainFragment();

            transaction.add(R.id.fl_body, booksMainFragment, "booksMainFragment");
            transaction.add(R.id.fl_body, seatsMainFragment, "seatsMainFragment");
            transaction.add(R.id.fl_body, lecturesMainFragment, "lecturesMainFragment");
            transaction.add(R.id.fl_body, myMainFragment, "myMainFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }


    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LogUtils.logd("transaction === " + transaction);
        switch (position) {
            //图书馆
            case 0:
                transaction.hide(seatsMainFragment);
                transaction.hide(lecturesMainFragment);
                transaction.hide(myMainFragment);
                transaction.show(booksMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //预约座位
            case 1:
                transaction.hide(booksMainFragment);
                transaction.hide(lecturesMainFragment);
                transaction.hide(myMainFragment);
                transaction.show(seatsMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //预约讲座
            case 2:
                transaction.hide(booksMainFragment);
                transaction.hide(seatsMainFragment);
                transaction.hide(myMainFragment);
                transaction.show(lecturesMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                transaction.hide(booksMainFragment);
                transaction.hide(seatsMainFragment);
                transaction.hide(lecturesMainFragment);
                transaction.show(myMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }


    @Override
    public void success(UseInfoBean infoBean) {
        LogUtils.logd("infoBean === " + infoBean.getUser().getHeadPic());
        this.infoBean = infoBean.getUser();

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