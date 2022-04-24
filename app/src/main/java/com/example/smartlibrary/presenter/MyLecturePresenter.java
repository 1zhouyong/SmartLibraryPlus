package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.MyLectureBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.contract.MyLectureContract;
import com.example.smartlibrary.model.MyLectureModel;
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
 * 2022/4/21 : Create MyLecturePresenter.java
 * -----------------------------------------------------------------
 */
public class MyLecturePresenter extends BasePresenter<MyLectureContract.View> implements MyLectureContract.Presenter {


    private final MyLectureContract.Model model;

    public MyLecturePresenter(){
        model = new MyLectureModel();
    }


    @Override
    public void submitMyLecture(String token, RequestBody body) {
        model.getMyLecture(token,body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseArrayBean<MyLectureBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseArrayBean<MyLectureBean> myLectureBeanBaseArrayBean) {
                        mView.getLectureListSuccess(myLectureBeanBaseArrayBean.getResult());
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
