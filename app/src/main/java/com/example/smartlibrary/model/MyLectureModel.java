package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.LectureBean;
import com.example.smartlibrary.bean.MyLectureBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MyLectureContract;
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
 * 2022/4/21 : Create MyLectureModel.java
 * -----------------------------------------------------------------
 */
public class MyLectureModel implements MyLectureContract.Model {
    @Override
    public Observable<BaseArrayBean<LectureBean>> getMyLecture(String token, RequestBody body) {
        return RetrofitClient.getInstance().getApi().getMyLecture(token,body);
    }
}
