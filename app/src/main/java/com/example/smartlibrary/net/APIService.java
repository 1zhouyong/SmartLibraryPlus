package com.example.smartlibrary.net;

import com.example.smartlibrary.bean.BaseArrayBean;
import com.example.smartlibrary.bean.BaseObjectBean;
import com.example.smartlibrary.bean.BookTypeBean;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    /**
     * 登陆
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @POST("login")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Observable<BaseObjectBean<String>> login(@Body RequestBody requestBody);


    @POST("book/select")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Observable<BaseArrayBean<BookTypeBean>> bookSelect(@Body RequestBody requestBody);

}
