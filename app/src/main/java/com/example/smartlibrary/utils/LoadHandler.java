package com.example.smartlibrary.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.smartlibrary.app.AppConstant;

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
 * 2022/4/28 : Create LoadHandler.java
 * -----------------------------------------------------------------
 */
public class LoadHandler extends Handler {
    private LoadListener listener;

    public LoadHandler(Looper looper) {
        super(looper);
    }

    public LoadHandler(LoadListener listener) {
        this.listener = listener;
    }
    public LoadHandler(Looper looper,LoadListener listener) {
        super(looper);
        this.listener = listener;
    }
    public LoadHandler() {
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle bundle = msg.getData();
        switch (msg.what) {
            case AppConstant.LOAD_START:// 开始加载
                if (listener != null) {
                    listener.onStart();
                }
                break;
            case AppConstant.LOAD_SUCCESS:// 加载成功
                if (listener != null) {
                    Object result = null;
                    if(bundle!=null){
                        result = bundle.get("result");
                    }
                    listener.onSuccess(result);
                }
                break;
            case AppConstant.LOAD_FAIL:// 加载失败
                if (listener != null) {
                    Object result = null;
                    if(bundle!=null){
                        result = bundle.get("result");
                    }
                    listener.onFail();
                    listener.onFail(result);
                }
                break;
            case AppConstant.LOAD_Littile_DATA:// 数据量少
                if (listener != null) {
                    Object result = null;
                    if(bundle!=null){
                        result = bundle.get("result");
                    }
                    listener.onLittleData(result);
                }
                break;
            case AppConstant.LOAD_HALF:// 加载一半
                if (listener != null) {
                    Object result = null;
                    if(bundle!=null){
                        result = bundle.get("result");
                    }
                    listener.onHalfLoad(result);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 一个抽象的加载监听接口
     * @author xiaoqian.hu
     *
     */
    public interface LoadListener {
        void onStart();
        void onLoading();
        void onFail();
        void onFail(Object result);
        void onSuccess(Object result);
        /**
         * 数据没有加载完成，但是足够程序使用时的回调
         * @param result
         */
        void onHalfLoad(Object result);
        void onLittleData(Object result);
    }
}



