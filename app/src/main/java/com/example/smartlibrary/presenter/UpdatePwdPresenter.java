package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.UpdatePwdContract;
import com.example.smartlibrary.model.UpdatePwdModel;
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
 * 2022/4/25 : Create UpdatePwdPresenter.java
 * -----------------------------------------------------------------
 */
public class UpdatePwdPresenter extends BasePresenter<UpdatePwdContract.View> implements UpdatePwdContract.Presenter {

    private final UpdatePwdContract.Model model;

    public UpdatePwdPresenter(){
        model = new UpdatePwdModel();
    }


    @Override
    public void updateNewPwd(String token, RequestBody body) {
        model.updatePwd(token,body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<Boolean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<Boolean> booleanBaseObjectBean) {
                        mView.updatePwdSuccess(booleanBaseObjectBean.getSuccess());
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
