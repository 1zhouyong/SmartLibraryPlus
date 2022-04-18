package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.LectureBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.contract.LectureMainContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;

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
 * 2022/4/18 : Create LectureMainModel.java
 * -----------------------------------------------------------------
 */
public class LectureMainModel implements LectureMainContract.Model {
    @Override
    public Observable<BaseArrayBean<LectureBean>> getLectureList(String token) {
        return RetrofitClient.getInstance().getApi().getLectureList(token);
    }
}
