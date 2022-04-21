package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseModel;
import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.MyLectureBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;

import java.util.List;

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
 * 2022/4/21 : Create MyLectureContract.java
 * -----------------------------------------------------------------
 */
public interface MyLectureContract {

    interface Model extends BaseModel{
        Observable<BaseArrayBean<MyLectureBean>> getMyLecture(String token, RequestBody body);
    }

    interface View extends BaseView{
        void getLectureListSuccess(List<MyLectureBean> beans);
    }

    interface Presenter{
        void submitMyLecture(String token,RequestBody body);
    }
}
