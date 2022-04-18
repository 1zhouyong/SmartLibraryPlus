package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseModel;
import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.LectureBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;

import java.util.List;

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
 * 2022/4/18 : Create LectureMainContract.java
 * -----------------------------------------------------------------
 */
public interface LectureMainContract {

    interface Model extends BaseModel{
        Observable<BaseArrayBean<LectureBean>> getLectureList(String token);
    }

    interface View extends BaseView{
        void getLectureSuccess(List<LectureBean> list);
    }

    interface Presenter{
        void selectLecture(String token);
    }
}
