package com.example.smartlibrary.widget;

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
 * 2022/4/15 : Create CustomSpinner.java
 * -----------------------------------------------------------------
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.smartlibrary.R;

import java.util.List;

/*** 自定义Spinner下拉列表 ***/
@SuppressLint("AppCompatCustomView")
public class CustomSpinner extends TextView {
    private Context mContext;
    /*** 获取当前选中的item*/
    private int selectedItemPosition = 0;

    private List<String> mListData = null;
    private PopupWindow mPopWindow = null;

    private CustomSpinnerAdapter mCustomSpinnerAdapter = null;
    private OnSpinnerItemClickListener mOnSpinnerItemClickListener = null;

    public CustomSpinner(Context context) {
        this(context, null);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化控件
     *
     * @param context
     */
    private void initView(Context context) {
        this.mContext = context;
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_spinner_down);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        setCompoundDrawables(null, null, drawable, null);
    }

    public void initData(List<String> listData) {
        setData(listData);
        setText(listData.get(selectedItemPosition));

        View spinnerItemPopView = LayoutInflater.from(mContext).inflate(R.layout.layout_spinner_popwindow, null);
        ListView listView = spinnerItemPopView.findViewById(R.id.spinner_pop_list);

        mCustomSpinnerAdapter = new CustomSpinnerAdapter();
        listView.setAdapter(mCustomSpinnerAdapter);

        mPopWindow = new PopupWindow(spinnerItemPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            setText(mListData.get(position));
            selectedItemPosition = position;
            mCustomSpinnerAdapter.notifyDataSetChanged();
            mPopWindow.dismiss();
            if (mOnSpinnerItemClickListener != null) {
                mOnSpinnerItemClickListener.OnSpinnerItemClick(parent, view, position, id);
            }
        });

        mPopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
        mPopWindow.setOnDismissListener(() -> {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_spinner_down);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            setCompoundDrawables(null, null, drawable, null);
        });
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setData(List<String> list) {
        if (null == list) {
            return;
        }
        this.mListData = list;
    }

    /**
     * 显示window
     */
    public void showSpinner() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_spinner_up);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        setCompoundDrawables(null, null, drawable, null);
        mPopWindow.showAsDropDown(this);
    }

    /**
     * 关闭当前的window
     */
    void closeSpinner() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

    class CustomSpinnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (null == mListData) {
                return 0;
            }
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_spinner_pop, null);
            }

            View divider = convertView.findViewById(R.id.divider);
            View up_divider = convertView.findViewById(R.id.up_divider);
            TextView textView = convertView.findViewById(R.id.type);
            ImageView imageView = convertView.findViewById(R.id.isSelected);

            if (position == 0) {
                up_divider.setVisibility(View.VISIBLE);
            } else {
                up_divider.setVisibility(View.GONE);
            }
            if (position == mListData.size() - 1) {
                divider.setVisibility(View.GONE);
            } else {
                divider.setVisibility(View.VISIBLE);
            }
            if (position == selectedItemPosition) {
                imageView.setVisibility(View.VISIBLE);
                textView.setTextColor(Color.parseColor("#99F47C30"));
            } else {
                imageView.setVisibility(View.GONE);
                textView.setTextColor(Color.parseColor("#99000000"));
            }
            textView.setText(mListData.get(position));
            return convertView;
        }
    }

    /*** 设置item 点击事件**/
    public void setOnSpinnerItemClickListener(OnSpinnerItemClickListener listener) {
        this.mOnSpinnerItemClickListener = listener;
    }

    public interface OnSpinnerItemClickListener {
        void OnSpinnerItemClick(AdapterView<?> parent, View view, int position, long id);
    }
}

