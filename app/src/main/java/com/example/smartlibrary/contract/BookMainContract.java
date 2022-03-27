package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.BaseArrayBean;
import com.example.smartlibrary.bean.BookTypeBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public interface BookMainContract {

    interface Model{
        Observable<BaseArrayBean<BookTypeBean>> bookSelect(RequestBody body);
    }

    interface View extends BaseView{
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(String errMessage);

        void onSuccess(List<BookTypeBean> bean);
    }

    interface Presenter {
        void selectBook(String bookType);
    }
}
