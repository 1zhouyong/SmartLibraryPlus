package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.LoginContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<BaseObjectBean<String>> login(RequestBody requestBody) {
        return RetrofitClient.getInstance().getApi().login(requestBody);
    }
}
