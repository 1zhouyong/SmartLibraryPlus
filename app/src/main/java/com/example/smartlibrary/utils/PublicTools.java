package com.example.smartlibrary.utils;

import com.example.smartlibrary.app.AppConstant;

public class PublicTools {

    public static String changeImageUrl(String uri){
        String imageUrl = uri.replace("\\","/").
                replace("D:/tmp/libary/web/src/main/resources/static/", AppConstant.baseUrl);
        LogUtils.logd("uri === " + uri +"\nimageUrl === "+imageUrl);
        return imageUrl;

    }
}
