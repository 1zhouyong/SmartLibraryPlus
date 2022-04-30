package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.BookCommentBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.BookInfoContract;
import com.example.smartlibrary.model.BookInfoModel;
import com.example.smartlibrary.net.RetrofitClient;
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
 * 2022/4/27 : Create BookInfoPresenter.java
 * -----------------------------------------------------------------
 */
public class BookInfoPresenter extends BasePresenter<BookInfoContract.View> implements BookInfoContract.Presenter {


    private final BookInfoContract.Model model;

    public BookInfoPresenter(){
        model = new BookInfoModel();
    }


    @Override
    public void submitBookComment(RequestBody body) {
        model.getBookComment(body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseArrayBean<BookCommentBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseArrayBean<BookCommentBean> bookCommentBeanBaseArrayBean) {
                        mView.getCommentSuccess(bookCommentBeanBaseArrayBean.getResult());
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
    public void addBookComment(RequestBody body) {
        model.addBookComment(body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<Boolean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<Boolean> booleanBaseObjectBean) {
                        mView.addBookCommentSuccess(booleanBaseObjectBean.getSuccess());
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
    public void addBookCommentReply(RequestBody body) {
        model.addBookCommentReply(body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<Boolean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<Boolean> booleanBaseObjectBean) {
                        mView.addBookCommentReplySuccess(booleanBaseObjectBean.getSuccess());
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
    public void setBookCommentPraise(RequestBody body) {
        model.addBookCommentReply(body)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<Boolean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<Boolean> booleanBaseObjectBean) {
                        mView.setBookCommentPraiseSuccess(booleanBaseObjectBean.getSuccess());
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
