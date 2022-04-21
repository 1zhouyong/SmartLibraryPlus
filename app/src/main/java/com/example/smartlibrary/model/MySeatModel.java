package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.MySeatBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MySeatContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

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
 * 2022/4/21 : Create MySeatModel.java
 * -----------------------------------------------------------------
 */
public class MySeatModel implements MySeatContract.Model {

    @Override
    public Observable<BaseObjectBean<MySeatBean>> getMySeat(String token, RequestBody body) {
        return RetrofitClient.getInstance().getApi().queryMySeat(token,body);
    }

    @Override
    public Observable<BaseObjectBean<Boolean>> cancelOrderSeat(String token, RequestBody body) {
        return RetrofitClient.getInstance().getApi().cancelOderSeat(token,body);
    }
}
