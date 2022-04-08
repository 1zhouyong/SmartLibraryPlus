package com.example.smartlibrary.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartlibrary.R;
import com.example.smartlibrary.app.AppConstant;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.bean.base.BaseObjectBean;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.ShareUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstant.baseUrl)
                .build();

        Api service = retrofit.create(Api.class);


        //1.创建MultipartBody.Builder对象
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型

//2.获取图片，创建请求体
        File file=new File("/storage/emulated/0/crop.jpeg");
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

    //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
//        builder.addFormDataPart("headPic", value);//传入服务器需要的key，和相应value值
        builder.addFormDataPart("headPic",file.getName(),body); //添加图片数据，body创建的请求体

//4.创建List<MultipartBody.Part> 集合，
//  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
//  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

//5.最后进行HTTP请求，传入parts即可

        Call<BaseObjectBean<String>> token = service.myUpload(ShareUtils.getString(BaseApplication.getAppContext(), "token"
                , ""), "1", parts);


        token.enqueue(new Callback<BaseObjectBean<String>>() {
            @Override
            public void onResponse(Call<BaseObjectBean<String>> call, Response<BaseObjectBean<String>> response) {
                LogUtils.logd("response === " + response);
            }

            @Override
            public void onFailure(Call<BaseObjectBean<String>> call, Throwable t) {

            }
        });
    }
}