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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    // 判断身份证号是否合法
    public static Boolean judgeId(String id)
    {
        Boolean result = true;

        // 长度不等于 18 位
        if(id.length() != 18) return false;

        // 系数算法
        String tempId = getStr(id,0,16);
        int[] coeff = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        char[] end = {'1','0','X','9','8','7','6','5','4','3','2'};
        int sum = 0;
        for (int i = 0; i < tempId.length(); i++)
        {
            int bye = tempId.charAt(i) - '0';
            sum += bye * coeff[i];
        }
        sum %= 11;
        if(end[sum] != getStr(id,17,17).charAt(0)) result = false;

        return result;
    }

    // 截取字符串的方法
    public static String getStr(String str,int a,int b)
    {
        b++;
        return str.substring(a,b);
    }

    /**
     * 正则表达式校验手机号码
     * @return false 则手机号码不合法，true 则手机号码校验通过
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            System.out.print("手机号应为11位数 ");
            return false;
        }else{
            String regPattern =  "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
            Pattern pattern = Pattern.compile(regPattern);
            Matcher matcher = pattern.matcher(phoneNumber);
            boolean isMatch = matcher.matches();
            if (!isMatch) {
                System.out.print("请填入正确的手机号 ");
            }
            return isMatch;
        }
    }


}
