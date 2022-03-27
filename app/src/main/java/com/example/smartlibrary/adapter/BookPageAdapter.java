package com.example.smartlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.bean.BookTypeBean;
import com.example.smartlibrary.utils.PublicTools;

import java.util.List;
import java.util.Random;

import imageloader.libin.com.images.config.ScaleMode;
import imageloader.libin.com.images.loader.ImageLoader;

public class BookPageAdapter extends BaseAdapter {

    private Context context;
    private List<BookTypeBean> bookTypeList;


    public BookPageAdapter(Context context, List<BookTypeBean> bookTypeList) {
        this.context = context;
        this.bookTypeList = bookTypeList;
    }


    @Override
    public int getCount() {
        return bookTypeList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookTypeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_book_type, viewGroup, false);
        }
        ViewHolder holder = new ViewHolder(view);
        holder.tv_book_name.setText(bookTypeList.get(i).getName());
        holder.tv_fire.setText(formateRate(nextDouble(1.0,10.0)) +"万");
        ImageLoader.with(context)
                .url(PublicTools.changeImageUrl(bookTypeList.get(i).getPicPath()))
                .rectRoundCorner(5)
                .scale(ScaleMode.FIT_CENTER)
                .into(holder.iv_book_pic);
        return view;
    }


    public static String nextDouble(final double min, final double max) {
        return String.valueOf(min + ((max - min) * new Random().nextDouble()));
    }

    /**
     * 保留小数点后两位小数
     * @param rateStr xx.xxxxx
     * @return result
     * */
    public static String formateRate(String rateStr) {
        if (rateStr.indexOf(".") != -1) {
            // 获取小数点的位置
            int num = 0;
            num = rateStr.indexOf(".");

            String dianAfter = rateStr.substring(0, num + 1);
            String afterData = rateStr.replace(dianAfter, "");

            return rateStr.substring(0, num) + "." + afterData.substring(0, 2);
        } else {
            if (rateStr == "1") {
                return "100";
            } else {
                return rateStr;
            }
        }
    }

    class ViewHolder {
        public View rootView;
        public ImageView iv_book_pic;
        public TextView tv_book_name;
        public TextView tv_fire;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_book_pic = (ImageView) rootView.findViewById(R.id.iv_book_pic);
            this.tv_book_name = (TextView) rootView.findViewById(R.id.tv_book_name);
            this.tv_fire = (TextView) rootView.findViewById(R.id.tv_fire);
        }

    }


}
