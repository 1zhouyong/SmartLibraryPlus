package com.example.smartlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary.bean.LectureBean;

import java.util.List;

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
 * 2022/4/18 : Create LectureListAdapter.java
 * -----------------------------------------------------------------
 */
public class LectureListAdapter extends RecyclerView.Adapter<LectureListAdapter.ViewHolder> {


    private Context context;
    private List<LectureBean> list;

    public LectureListAdapter(Context context, List<LectureBean> list){
        this.context = context;
        this.list = list;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lecture, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LectureBean lecture = list.get(position);
        holder.tv_lecture_name.setText(lecture.getName());
        holder.tv_lecture_date.setText(lecture.getOrderNumber()+"/"+lecture.getSumNumber()+"\t\t\t\t\t"+
                lecture.getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_lecture_name;
        public TextView tv_lecture_date;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv_lecture_name = (TextView) rootView.findViewById(R.id.tv_lecture_name);
            this.tv_lecture_date = (TextView) rootView.findViewById(R.id.tv_lecture_date);
        }

    }
}
