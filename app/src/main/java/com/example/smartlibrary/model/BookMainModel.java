package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.BookTypeBean;
import com.example.smartlibrary.contract.BookMainContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public class BookMainModel implements BookMainContract.Model {
    @Override
    public Observable<BaseArrayBean<BookTypeBean>> bookSelect(RequestBody body) {
        return RetrofitClient.getInstance().getApi().bookSelect(body);
    }
}
