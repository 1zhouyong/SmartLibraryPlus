package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.MyMainContract;
import com.example.smartlibrary.model.MyMainModel;
import com.example.smartlibrary.net.RxScheduler;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.MultipartBody;

public class MyMainPresenter extends BasePresenter<MyMainContract.View> implements MyMainContract.Presenter{

    private MyMainContract.Model model;

    public MyMainPresenter(){
        model = new MyMainModel();
    }


    @Override
    public void uploadPic(String header , String id, List<MultipartBody.Part> file) {
        Observable<BaseObjectBean<String>> observable = model.uploadPic(header,id, file);
        observable.compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<String> stringBaseObjectBean) {
                        mView.onSuccess(stringBaseObjectBean.getResult());
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
