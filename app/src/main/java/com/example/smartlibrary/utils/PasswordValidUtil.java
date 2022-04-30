package com.example.smartlibrary.utils;

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
 * 2022/4/25 : Create PasswordValidUtil.java
 * -----------------------------------------------------------------
 */
public class PasswordValidUtil {

    //密码只能由大写字母，小写字母，数字构成
    public static boolean valid1(String password) {
        //检查每个字符，如果非大写字母，小写字母，数字则返回false
        for (char c : password.toCharArray()) {
            if (!(Character.isUpperCase(c) || Character.isLowerCase(c) || Character.isDigit(c))) {
                return false;
            }
        }
        return true;
    }

    //如果以数字开头返回false
    public static boolean valid2(String password) {
        if (Character.isDigit(password.charAt(0))) {
            return false;
        }
        return true;
    }

    //密码中至少出现大写字母，小写字母，数字中的两种
    public static boolean valid3(String password) {
        int isUpperCase = 0;
        int isLowerCase = 0;
        int isDigit = 0;
        for (char c : password.toCharArray()) {
            if (isUpperCase + isLowerCase + isDigit >= 2) {
                return true;
            }
            if (Character.isUpperCase(c)) {
                isUpperCase = 1;
            }
            if (Character.isLowerCase(c)) {
                isLowerCase = 1;
            }
            if (Character.isDigit(c)) {
                isDigit = 1;
            }
        }
        return false;
    }

    public static boolean valid4(String password) {

        return valid1(password) && valid2(password) && valid3(password) && password.length() >= 8;

    }
}

