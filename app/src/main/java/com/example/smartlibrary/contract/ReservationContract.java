package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public interface ReservationContract {

    interface Model{
        Observable<BaseArrayBean<SeatListBean>> getSeatList(String header, RequestBody requestBody);
    }

    interface View extends BaseView {
        void success(List<SeatListBean> list);

    }



    interface Presenter{
        void getSeatList(String head , RequestBody requestBody);
    }
}
