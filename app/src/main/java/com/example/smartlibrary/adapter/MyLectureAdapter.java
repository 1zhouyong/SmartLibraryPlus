package com.example.smartlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.bean.MyLectureBean;

import java.util.List;

/**
 * author: ${周勇}
 * Date: 2020/4/20
 * Description:{}
 */
public class MyLectureAdapter extends BaseAdapter {

    private Context context;
    private List<MyLectureBean> list;

    public MyLectureAdapter(Context context, List<MyLectureBean> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_lecture, viewGroup, false);
        }
        ViewHolder holder = new ViewHolder(view);
        holder.tv_lecture_name.setText(list.get(i).getName());
        holder.tv_lecture_teacher.setText("主讲人： "+list.get(i).getTeacher());
        return view;
    }

    public static
    class ViewHolder {
        public View rootView;
        public TextView tv_lecture_name;
        public TextView tv_lecture_teacher;
        public TextView tv_lecture_date;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_lecture_name = (TextView) rootView.findViewById(R.id.tv_lecture_name);
            this.tv_lecture_teacher = (TextView) rootView.findViewById(R.id.tv_lecture_teacher);
            this.tv_lecture_date = (TextView) rootView.findViewById(R.id.tv_lecture_date);
        }

    }


}
