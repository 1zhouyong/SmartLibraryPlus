package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MyMainContract;
import com.example.smartlibrary.net.RetrofitClient;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;

public class MyMainModel implements MyMainContract.Model {
    @Override
    public Observable<BaseObjectBean<String>> uploadPic(String header, String id, List<MultipartBody.Part>  file) {
        return RetrofitClient.getInstance().getApi().uploadPic(header,id,file);
    }
}
