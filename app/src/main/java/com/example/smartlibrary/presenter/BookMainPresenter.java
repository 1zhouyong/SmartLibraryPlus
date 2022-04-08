package com.example.smartlibrary.presenter;

import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.BookTypeBean;
import com.example.smartlibrary.contract.BookMainContract;
import com.example.smartlibrary.model.BookMainModel;
import com.example.smartlibrary.net.RxScheduler;
import com.example.smartlibrary.utils.mapToRequestBodyUtil;

import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class BookMainPresenter extends BasePresenter<BookMainContract.View> implements BookMainContract.Presenter {

    private BookMainContract.Model model;

    public BookMainPresenter(){
        model = new BookMainModel();
    }

    @Override
    public void selectBook(String bookType) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bookType",bookType);
        Observable<BaseArrayBean<BookTypeBean>> observable = model.bookSelect(mapToRequestBodyUtil.convertMapToBody(map));
        observable.compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseArrayBean<BookTypeBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseArrayBean<BookTypeBean> bookTypeBeanBaseArrayBean) {
                        if (bookTypeBeanBaseArrayBean.getSuccess()){
                            mView.onSuccess(bookTypeBeanBaseArrayBean.getResult());
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
