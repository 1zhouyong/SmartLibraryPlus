package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.UseInfoBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MainContract;
import com.example.smartlibrary.model.MainModel;
import com.example.smartlibrary.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {


    private MainContract.Model model;

    public MainPresenter() {
        model = new MainModel();
    }

    @Override
    public void getInfo(String token) {
        model.getInfo(token)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<BaseObjectBean<UseInfoBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<UseInfoBean> useInfoBeanBaseObjectBean) {

                        mView.success(useInfoBeanBaseObjectBean.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
