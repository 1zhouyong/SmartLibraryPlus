package com.example.smartlibrary.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startProgressDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }
}