package com.example.smartlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartlibrary.R;

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
 * 2022/4/15 : Create SeatPageAdapter.java
 * -----------------------------------------------------------------
 */
public class SeatPageAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public SeatPageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view_seat = LayoutInflater.from(context).inflate(R.layout.item_seat, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view_seat);
        if (list.get(i).equals("0")){
            viewHolder.iv_seat.setText("08:00-20:00(0/1)"+"\n可预约");
        }else {
            viewHolder.iv_seat.setText("08:00-20:00(1/1)"+"\n不可预约");
            viewHolder.iv_seat.setBackgroundColor(Color.parseColor("#8B8878"));
        }
        return view_seat;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView iv_seat;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_seat = (TextView) rootView.findViewById(R.id.iv_seat);
        }

    }
}

