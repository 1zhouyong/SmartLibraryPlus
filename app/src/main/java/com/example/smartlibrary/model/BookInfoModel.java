package com.example.smartlibrary.model;

import com.example.smartlibrary.bean.BookCommentBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.contract.BookInfoContract;
import com.example.smartlibrary.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;
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
 * 2022/4/27 : Create BookCommentModel.java
 * -----------------------------------------------------------------
 */
public class BookInfoModel implements BookInfoContract.Model {
    @Override
    public Observable<BaseArrayBean<BookCommentBean>> getBookComment(RequestBody body) {
        return RetrofitClient.getInstance().getApi().queryBookComment(body);
    }

    @Override
    public Observable<BaseObjectBean<Boolean>> addBookComment(RequestBody body) {
        return RetrofitClient.getInstance().getApi().addBookComment(body);
    }

    @Override
    public Observable<BaseObjectBean<Boolean>> addBookCommentReply(RequestBody body) {
        return RetrofitClient.getInstance().getApi().addBookCommentReply(body);
    }

    @Override
    public Observable<BaseObjectBean<Boolean>> setBookCommentPraise(RequestBody body) {
        return RetrofitClient.getInstance().getApi().setBookCommentPraise(body);
    }
}
