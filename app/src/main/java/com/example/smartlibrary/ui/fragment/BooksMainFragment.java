package com.example.smartlibrary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.channel.ChannelBean;
import com.example.smartlibrary.base.BaseFragment;
import com.example.smartlibrary.base.BaseFragmentAdapter;
import com.example.smartlibrary.ui.activity.BookTypeChooseActivity;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.MyUtils;
import com.example.smartlibrary.utils.ShareUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

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
 * 2022/3/23 : Create BooksMainFragment.java
 * -----------------------------------------------------------------
 */
public class BooksMainFragment extends BaseFragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.add_channel_iv)
    ImageView addChannelIv;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String[] bookTypeArray = new String[]{
            "哲学", "宗教", "经济", "文化", "科学", "教育", "体育", "语言", "医药", "卫生",
            "艺术", "历史", "地理", "文学", "科学", "农业", "工业", "交通", "航空", "环境"
    };
    private List<ChannelBean> myChannelList;
    private List<ChannelBean> othersChannelList;
    private BaseFragmentAdapter fragmentAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShareData();

    }

    private void initShareData() {
        myChannelList = ShareUtils.getList(getActivity(), "MyChannel");
        othersChannelList = ShareUtils.getList(getActivity(), "OthersChannel");
        LogUtils.logd("myChannelList === " + myChannelList);
    }

    @Override
    protected void initView(View view) {

        initTabAndViewPager();


    }

    private void initTabAndViewPager() {
        List<String> channelNames = new ArrayList<>();
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < myChannelList.size(); i++) {
            for (int j = 0; j < bookTypeArray.length; j++) {
                if (bookTypeArray[i].equals(myChannelList.get(j))) {

                }
            }
        }
        for (ChannelBean channelName : myChannelList) {
            channelNames.add(channelName.getSrc());
            mNewsFragmentList.add(createListFragments());
        }
        if (fragmentAdapter == null) {
            fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
        } else {
            //刷新fragment
            fragmentAdapter.setFragments(getChildFragmentManager(), mNewsFragmentList, channelNames);
        }
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
    }

    private Fragment createListFragments() {
        BookPageFragment fragment = new BookPageFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.add_channel_iv)
    public void onViewClicked() {
        List<ChannelBean> bookTypeChannelList = new ArrayList<>();
        bookTypeChannelList.add((new ChannelBean("我的频道", 1)));
        bookTypeChannelList.addAll(myChannelList);
        bookTypeChannelList.add((new ChannelBean("其它频道", 1)));
        bookTypeChannelList.addAll(othersChannelList);
        Bundle bundle = new Bundle();
        bundle.putSerializable("BookTypeChannelList", (Serializable) bookTypeChannelList);
        Intent intent = new Intent(getActivity(), BookTypeChooseActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtils.logd("requestCode1 === " + requestCode + ",resultCode === " + RESULT_OK);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            initShareData();
            initTabAndViewPager();
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
