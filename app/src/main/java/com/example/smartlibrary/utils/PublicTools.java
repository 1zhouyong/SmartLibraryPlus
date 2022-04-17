package com.example.smartlibrary.utils;

import android.content.Context;

import com.example.smartlibrary.app.AppConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getNowDay(int i){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, i);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        String time = sdf.format(c.getTime());
        return time;
    }

    public static String getNowDay1(int i){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, i);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        String time = sdf.format(c.getTime());
        return time;
    }

    //传入：asset文件夹中json文件名
    //返回：读取的String
    public static String readJsonFile(Context context , String file){
        StringBuilder newstringBuilder = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().getAssets().open(file);

            InputStreamReader isr = new InputStreamReader(inputStream);

            BufferedReader reader = new BufferedReader(isr);

            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data =  newstringBuilder.toString();
        return data;
    }

}
