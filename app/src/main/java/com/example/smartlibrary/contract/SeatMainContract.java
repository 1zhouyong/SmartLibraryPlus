package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public interface SeatMainContract {

    interface Model{
        Observable<BaseArrayBean<SeatListBean>> getSeatList(String header, RequestBody requestBody);

        Observable<BaseObjectBean<Boolean>> orderSeat(String token, RequestBody body);
    }

    interface View extends BaseView {
        void success(List<SeatListBean> list);


        void orderSeatSuccess(Boolean isSuccess);
    }



    interface Presenter{
        void getSeatList(String head , RequestBody requestBody);

        void order(String header,RequestBody body);
    }
}
