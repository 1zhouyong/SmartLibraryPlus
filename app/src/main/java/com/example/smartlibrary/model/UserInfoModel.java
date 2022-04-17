package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.UserInfoContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public class UserInfoModel implements UserInfoContract.Model {
    @Override
    public Observable<BaseObjectBean<Boolean>> updateInfo(String token, RequestBody body) {
        return RetrofitClient.getInstance().getApi().updateInfo(token,body);
    }
}
