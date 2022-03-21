package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.BaseObjectBean;
import com.example.smartlibrary.bean.LoginBean;
import com.example.smartlibrary.bean.LoginBodyBean;
import com.example.smartlibrary.contract.LoginContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<BaseObjectBean<String>> login(String username, String password) {
        return RetrofitClient.getInstance().getApi().login(new LoginBodyBean(username,password));
    }
}
