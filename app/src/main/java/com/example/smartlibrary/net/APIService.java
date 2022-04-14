package com.example.smartlibrary.net;

import com.example.smartlibrary.bean.SeatListBean;
import com.example.smartlibrary.bean.UseInfoBean;
import com.example.smartlibrary.bean.base.BaseArrayBean;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.bean.BookTypeBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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


    /**
     *      图书分类获取接口
     * @param requestBody
     * @return
     */
    @POST("book/select")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Observable<BaseArrayBean<BookTypeBean>> bookSelect(@Body RequestBody requestBody);


    /**
     *      修改头像接口
     * @param token
     * @param id
     * @param partLis
     * @return
     */
    @Multipart                          //这里用Multipart
    @POST("user/modifyHeadPic")                //请求方法为POST，里面为你要上传的url
    Observable<BaseObjectBean<String>> uploadPic(@Header("Login-Pass")String token ,
                                          @Query("id") String id,
                                          @Part List<MultipartBody.Part> partLis);


    /**
     *  得到用户信息
     * @param token
     * @return
     */

    @POST("getInfo")
    Observable<BaseObjectBean<UseInfoBean>> getInfo(@Header("Login-Pass")String token );



    @POST("seat/listByCondition")
    Observable<BaseArrayBean<SeatListBean>> getSeatList(@Header("Login-Pass")String token,@Body RequestBody body);

}
