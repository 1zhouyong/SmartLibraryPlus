package com.example.smartlibrary.demo;

import com.example.smartlibrary.bean.base.BaseObjectBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
    @Multipart                          //这里用Multipart
    @POST("user/modifyHeadPic")                //请求方法为POST，里面为你要上传的url
    Call<BaseObjectBean<String>> myUpload(@Header("Login-Pass")String token ,
                                          @Query("id") String id,
                                          @Part List<MultipartBody.Part> partLis);
    //注解用@Part，参数类型为List<MultipartBody.Part> 方便上传其它需要的参数或多张图片
    //Result为我自定义的一个类
}
