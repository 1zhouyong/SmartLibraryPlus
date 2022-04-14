package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.contract.ReservationContract;
import com.example.smartlibrary.model.ReservationModel;
import com.example.smartlibrary.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.RequestBody;

public class ReservationPresenter extends BasePresenter<ReservationContract.View> implements ReservationContract.Presenter{

    private ReservationContract.Model model;

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
}
