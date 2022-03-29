package com.example.smartlibrary.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.bean.BookTypeBean;
import com.example.smartlibrary.utils.PublicTools;

import butterknife.BindView;
import imageloader.libin.com.images.config.ScaleMode;
import imageloader.libin.com.images.loader.ImageLoader;

public class BookInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_book_author)
    TextView tvBookAuthor;
    @BindView(R.id.tv_book_publish)
    TextView tvBookPublish;
    @BindView(R.id.tv_book_publish_time)
    TextView tvBookPublishTime;
    @BindView(R.id.tv_book_desc)
    TextView tvBookDesc;
    @BindView(R.id.iv_boo_pic)
    ImageView ivBookPic;
    private BookTypeBean bookType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        bookType = (BookTypeBean) bundle.getSerializable("book");
        tvBookName.setText(bookType.getName());
        tvBookAuthor.setText(bookType.getAuthor());
        tvBookPublish.setText(bookType.getPublishAddress());
        tvBookPublishTime.setText(PublicTools.dateToString(bookType.getPublishTime()));
        ImageLoader.with(this)
                .url(PublicTools.changeImageUrl(bookType.getPicPath()))
                .rectRoundCorner(5)
                .scale(ScaleMode.FIT_CENTER)
                .into(ivBookPic);
        tvBookDesc.setText(bookType.getDescb());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_info;
    }

    @Override
    public void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}