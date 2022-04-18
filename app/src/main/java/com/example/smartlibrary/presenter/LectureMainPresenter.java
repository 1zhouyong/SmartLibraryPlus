package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.LectureBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.contract.LectureMainContract;
import com.example.smartlibrary.model.LectureMainModel;
import com.example.smartlibrary.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

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
 * 2022/4/18 : Create LectureMainPresenter.java
 * -----------------------------------------------------------------
 */
public class LectureMainPresenter extends BasePresenter<LectureMainContract.View> implements LectureMainContract.Presenter{

    private LectureMainContract.Model model;

    public LectureMainPresenter(){
        model = new LectureMainModel();
    }

    @Override
    public void selectLecture(String token) {
        model.getLectureList(token)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseArrayBean<LectureBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseArrayBean<LectureBean> lectureBeanBaseArrayBean) {
                        mView.getLectureSuccess(lectureBeanBaseArrayBean.getResult());
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
