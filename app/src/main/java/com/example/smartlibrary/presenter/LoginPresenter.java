package com.example.smartlibrary.presenter;

import android.util.Log;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.BaseObjectBean;
import com.example.smartlibrary.contract.LoginContract;
import com.example.smartlibrary.model.LoginModel;
import com.example.smartlibrary.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.Model model;

    public LoginPresenter() {
        model = new LoginModel();
    }


    @Override
    public void login(String username, String password) {

        model.login(username, password)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<BaseObjectBean<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "onSubscribe---");
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<String> stringBaseObjectBean) {
                        System.out.println("e === " + stringBaseObjectBean);
                        mView.onSuccess(stringBaseObjectBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError---");
                        mView.onError(e.getMessage());
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });


    }
}
