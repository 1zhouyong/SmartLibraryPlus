package com.example.smartlibrary.utils;

import com.example.smartlibrary.app.AppConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicTools {

    public static String changeImageUrl(String uri) {
        String imageUrl = uri.replace("\\", "/").
                replace("D:/tmp/libary/web/src/main/resources/static/", AppConstant.baseUrl);
        LogUtils.logd("uri === " + uri + "\nimageUrl === " + imageUrl);
        return imageUrl;
    }

    public static String changeImageUrl1(String uri) {
        String imageUrl = uri.replace("\\", "/").
                replace("/Users/apple/Desktop/libary/web/src/main/resources/static/", AppConstant.baseUrl);
        LogUtils.logd("uri === " + uri + "\nimageUrl === " + imageUrl);
        return imageUrl;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String format = sdf.format(date);
        return format;
    }
}
