package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseModel;
import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.MySeatBean;
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
 * 2022/4/21 : Create MySeatContract.java
 * -----------------------------------------------------------------
 */
public interface MySeatContract {

    interface Model extends BaseModel{
        Observable<BaseObjectBean<MySeatBean>>  getMySeat(String token, RequestBody body);

        Observable<BaseObjectBean<Boolean>> cancelOrderSeat(String token,RequestBody body);

    }

    interface View extends BaseView{
        void getMySeatSuccess(MySeatBean bean, String date);

        void cancelSucees(int postion);
    }

    interface Presenter {
        void submit(String token,RequestBody body,String date);

        void cancelSeat(String token,RequestBody body,int postion);
    }
}
