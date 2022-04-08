package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.base.BaseObjectBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;

public interface MyMainContract {

    interface Model{
        Observable<BaseObjectBean<String>> uploadPic(String header, String id, List<MultipartBody.Part> file);
    }

    interface View extends BaseView{
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(String errMessage);

        void onSuccess(String msg);
    }

    interface Presenter{
        void uploadPic(String header , String id, List<MultipartBody.Part> file);
    }
}
