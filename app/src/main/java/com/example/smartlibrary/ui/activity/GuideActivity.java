package com.example.smartlibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.MyPagerAdapter;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.utils.DensityUtil;
import com.example.smartlibrary.utils.ShareUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {


    public static final String START_MAIN = "start_main";
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.btn_start_main)
    Button btnStartMain;
    @BindView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @BindView(R.id.iv_red_point)
    ImageView ivRedPoint;

    private ArrayList<ImageView> imageViews;

    /**
     * 两点间的间距
     */
    private int leftMarg;
    private int widthDpi;


    /**
     * 设置适配器--> 准备数据
     */
    private int ids[] = new int[]{
            R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {

    }

    /**
     * 初始化引导页的数据
     */
    private void initData() {
        widthDpi = DensityUtil.dp2px(this, 10);
        Log.e("GuideActivity", widthDpi + "-----------------");

        imageViews = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {

            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //把图片添加到集合中
            imageViews.add(imageView);

            //添加灰色的点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.point_gray);

            //设置点的大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDpi, widthDpi);
            if (i != 0) {
                //设置间距
                params.leftMargin = widthDpi;
            }
            point.setLayoutParams(params);

            llPointGroup.addView(point);
        }

        //设置适配器
        viewPager.setAdapter(new MyPagerAdapter(imageViews));

        /**
         * 求间距
         * 构造方法--> 测量(measure--onMeasure)--> layout-->onLayout-->draw-->onDraw
         */
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //监听viewPager页面滑动的百分比
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        //设置点击事件
        btnStartMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录进入引导页面
                ShareUtils.putBoolean(GuideActivity.this, START_MAIN, true);
                //跳转到主页面
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));

                finish();//关闭引导页面
            }
        });
    }






    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * @param position             当前滑动页面的下标位置
         * @param positionOffset       滑动了页面的百分比
         * @param positionOffsetPixels 滑动了页面多少像数
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //红点移动的距离 = ViewPager页面的百分比* 间距

//            float leftMargin = positionOffset * leftMarg;
            //坐标 = 起始位置 + 红点移动的距离；

            float leftMargin = (position + positionOffset) * leftMarg;
            RelativeLayout.LayoutParams paramgs = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
            //距离左边的距离
            paramgs.leftMargin = (int) leftMargin;
            ivRedPoint.setLayoutParams(paramgs);

        }

        @Override
        public void onPageSelected(int position) {
            if (position == imageViews.size() - 1) {//滑动到最后一个页面显示按钮
                //让按钮显示
                btnStartMain.setVisibility(View.VISIBLE);
            } else {
                //按钮隐藏
                btnStartMain.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {

            ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            //间距 = 第一个点距离左边距离 - 第0个点距离左边距离
            leftMarg = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();

        }
    }

}