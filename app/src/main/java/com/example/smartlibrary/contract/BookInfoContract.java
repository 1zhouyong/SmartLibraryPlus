package com.example.smartlibrary.contract;

import com.example.smartlibrary.base.BaseModel;
import com.example.smartlibrary.base.BaseView;
import com.example.smartlibrary.bean.BookCommentBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;

import java.util.List;

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
 * 2022/4/27 : Create BookInfoContract.java
 * -----------------------------------------------------------------
 */
public interface BookInfoContract {

    interface Model extends BaseModel {
        Observable<BaseArrayBean<BookCommentBean>> getBookComment(RequestBody body);

        Observable<BaseObjectBean<Boolean>> addBookComment(RequestBody body);

        Observable<BaseObjectBean<Boolean>> addBookCommentReply(RequestBody body);

        Observable<BaseObjectBean<Boolean>> setBookCommentPraise(RequestBody body);

    }


    interface View extends BaseView {

        void getCommentSuccess(List<BookCommentBean> commentBeanList);

        void addBookCommentSuccess(boolean isSuccess);

        void addBookCommentReplySuccess(boolean isSuccess);

        void setBookCommentPraiseSuccess(boolean isSuccess);
    }

    interface Presenter{

        void submitBookComment(RequestBody body);

        void addBookComment(RequestBody body);

        void addBookCommentReply(RequestBody body);

        void setBookCommentPraise(RequestBody body);
    }
}
