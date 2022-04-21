package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseModel;
import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.base.BaseObjectBean;

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
 * 2022/4/20 : Create LectureInfoContract.java
 * -----------------------------------------------------------------
 */
public interface LectureInfoContract {

    interface Model extends BaseModel {
        Observable<BaseObjectBean<Boolean>> orderLecture(String token, RequestBody body);
    }

    interface View extends BaseView {
        void orderSuccess(boolean isSuccess);
    }

    interface Presenter{
        void submitOrderLecture(String token,RequestBody body);
    }
}
