package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.SeatMainContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public class ReservationModel implements SeatMainContract.Model {
    @Override
    public Observable<BaseArrayBean<SeatListBean>> getSeatList(String header, RequestBody requestBody) {
        return RetrofitClient.getInstance().getApi().getSeatList(header,requestBody);
    }

    @Override
    public Observable<BaseObjectBean<Boolean>> orderSeat(String token, RequestBody body) {
        return RetrofitClient.getInstance().getApi().orderSeat(token,body);
    }
}
