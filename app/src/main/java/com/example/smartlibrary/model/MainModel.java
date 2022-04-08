package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.UseInfoBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MainContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;

public class MainModel implements MainContract.Model {

    @Override
    public Observable<BaseObjectBean<UseInfoBean>> getInfo(String token) {
        return RetrofitClient.getInstance().getApi().getInfo(token);
    }
}
