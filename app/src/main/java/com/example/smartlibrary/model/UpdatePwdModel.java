package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.UpdatePwdContract;
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
 * 2022/4/25 : Create UpdatePwdModel.java
 * -----------------------------------------------------------------
 */
public class UpdatePwdModel implements UpdatePwdContract.Model {
    @Override
    public Observable<BaseObjectBean<Boolean>> updatePwd(String token, RequestBody body) {
        return RetrofitClient.getInstance().getApi().UpdatePwd(token,body);
    }
}
