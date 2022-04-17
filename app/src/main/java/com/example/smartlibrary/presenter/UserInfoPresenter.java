package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.UserInfoContract;
import com.example.smartlibrary.model.UserInfoModel;
import com.example.smartlibrary.net.RxScheduler;
import com.example.smartlibrary.utils.LogUtils;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.RequestBody;

public class UserInfoPresenter extends BasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter{


    private final UserInfoContract.Model model;

    public UserInfoPresenter(){
        model = new UserInfoModel();
    }


    @Override
    public void updateInfo(String token, RequestBody body) {
        model.updateInfo(token,body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<Boolean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<Boolean> booleanBaseObjectBean) {
                        mView.updateInfoSuccess(booleanBaseObjectBean.getSuccess());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.logd("e === " + e.toString());
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
