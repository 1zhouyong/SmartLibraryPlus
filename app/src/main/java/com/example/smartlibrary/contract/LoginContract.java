package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.base.BaseObjectBean;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public interface LoginContract {

    interface Model {
        Observable<BaseObjectBean<String>> login(RequestBody requestBody);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(String errMessage);

        void onSuccess(BaseObjectBean<String> bean);
    }

    interface Presenter {
        /**
         * 登陆
         *
         * @param username
         * @param password
         */
        void login(String username, String password);
    }
}
