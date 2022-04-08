package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.UseInfoBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;

import io.reactivex.rxjava3.core.Observable;


public interface MainContract {

    interface Model{
        Observable<BaseObjectBean<UseInfoBean>>  getInfo(String token);
    }

    interface View extends BaseView{
        void success(UseInfoBean infoBean);
    }

    interface Presenter {
        void getInfo(String token);
    }
}
