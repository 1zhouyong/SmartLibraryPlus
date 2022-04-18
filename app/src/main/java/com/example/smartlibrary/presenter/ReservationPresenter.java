package com.example.smartlibrary.presenter;

import android.widget.Toast;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.SeatMainContract;
import com.example.smartlibrary.model.ReservationModel;
import com.example.smartlibrary.net.RxScheduler;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.ToastUitl;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.RequestBody;

public class ReservationPresenter extends BasePresenter<SeatMainContract.View> implements SeatMainContract.Presenter{

    private SeatMainContract.Model model;

    public ReservationPresenter(){
        model = new ReservationModel();
    }



    @Override
    public void getSeatList(String head, RequestBody requestBody) {
        model.getSeatList(head,requestBody)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseArrayBean<SeatListBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseArrayBean<SeatListBean> seatListBeanBaseObjectBean) {
                        mView.success(seatListBeanBaseObjectBean.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void order(String header, RequestBody body) {
        model.orderSeat(header,body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<Boolean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<Boolean> booleanBaseObjectBean) {
                        LogUtils.logd("Throwable == " + booleanBaseObjectBean.getErrorMsg());
                        mView.orderSeatSuccess(booleanBaseObjectBean.getSuccess());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.logd("Throwable == " + e.toString());
                        if (e.toString().contains("exisit")){
                            ToastUitl.show("您今天已经预约过座位，不能预约多个", Toast.LENGTH_SHORT);
                        }
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
