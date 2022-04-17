package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseModel;
import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.base.BaseObjectBean;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public interface UserInfoContract {

    interface Model extends BaseModel{
        Observable<BaseObjectBean<Boolean>> updateInfo(String token,RequestBody body);
    }

    interface View extends BaseView{

        void updateInfoSuccess(boolean isSuccess);
    }

    interface Presenter{
        void updateInfo(String token , RequestBody body);
    }
}
