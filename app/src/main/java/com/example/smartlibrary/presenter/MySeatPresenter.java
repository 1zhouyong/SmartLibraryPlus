package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.MySeatBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MySeatContract;
import com.example.smartlibrary.model.MySeatModel;
import com.example.smartlibrary.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.RequestBody;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2011-2022, by your Signway, All rights reserved.
 * -----------------------------------------------------------------
 *
 * ProjectName: SmartLibrary
 *
 * Author: ZY
 *
 * Email: yong.zhou@signway.cn
 *
 * Description:
 *
 * -----------------------------------------------------------------
 * 2022/4/21 : Create MySeatPresenter.java
 * -----------------------------------------------------------------
 */
public class MySeatPresenter extends BasePresenter<MySeatContract.View> implements MySeatContract.Presenter {

    private final MySeatContract.Model model;

    public MySeatPresenter(){
        model = new MySeatModel();
    }


    @Override
    public void submit(String token, RequestBody body) {
        model.getMySeat(token,body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<MySeatBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<MySeatBean> mySeatBeanBaseObjectBean) {
                        mView.getMySeatSuccess(mySeatBeanBaseObjectBean.getResult());
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
    public void cancelSeat(String token, RequestBody body ,int postion) {
        model.cancelOrderSeat(token,body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<Boolean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<Boolean> booleanBaseObjectBean) {
                        if (booleanBaseObjectBean.getSuccess()){
                            mView.cancelSucees(postion);
                        }
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
