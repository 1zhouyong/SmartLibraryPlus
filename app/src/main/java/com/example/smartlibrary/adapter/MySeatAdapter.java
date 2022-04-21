package com.example.smartlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.bean.MySeatBean;
import com.example.smartlibrary.contract.MySeatContract;
import com.example.smartlibrary.ui.activity.MainActivity;
import com.example.smartlibrary.utils.PublicTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
 * 2022/4/21 : Create MySeatAdapter.java
 * -----------------------------------------------------------------
 */
public class MySeatAdapter extends RecyclerView.Adapter<MySeatAdapter.ViewHolder> {

    private Context context;
    private List<MySeatBean> list;
    private String name;
    private String studyId;
    private CancelCallBack cancelCallBack;

    public MySeatAdapter(Context context, List<MySeatBean> list){
        this.context = context;
        this.list = list;
    }

    public void setCancelCallBack(CancelCallBack cancelCallBack) {
        this.cancelCallBack = cancelCallBack;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_seat_info, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MySeatBean mySeatBean = list.get(position);
        holder.tv_time.setText(new SimpleDateFormat("hh:mm:ss").format(new Date()));
        holder.tv_order_num.setText("订单编号：#" + getRandom(6));
        holder.tv_name.setText("客户：" + name);
        holder.tv_student_num.setText("学号：" + studyId);
        holder.tv_site.setText("空间预约：" + mySeatBean.getPlace() + "——" + mySeatBean.getNumber());
        holder.tv_date.setText("时间段：" + PublicTools.getNowDay(position) + "08:00-20:00");
        holder.btn_cancel_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCallBack.cancel(position);
            }
        });
    }

    private long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min;
        return rangeLong;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_time;
        public TextView tv_order_num;
        public TextView tv_name;
        public TextView tv_student_num;
        public TextView tv_site;
        public TextView tv_date;
        public Button btn_cancel_site;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_order_num = (TextView) rootView.findViewById(R.id.tv_order_num);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_student_num = (TextView) rootView.findViewById(R.id.tv_student_num);
            this.tv_site = (TextView) rootView.findViewById(R.id.tv_site);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.btn_cancel_site = (Button) rootView.findViewById(R.id.btn_cancel_site);
        }

    }

    public interface CancelCallBack{
        void cancel(int position);
    }
}
