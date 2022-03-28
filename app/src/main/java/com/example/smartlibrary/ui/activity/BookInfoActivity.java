package com.example.smartlibrary.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.bean.BookTypeBean;
import com.example.smartlibrary.utils.PublicTools;

import butterknife.BindView;

public class BookInfoActivity extends BaseActivity {

    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_book_author)
    TextView tvBookAuthor;
    @BindView(R.id.tv_book_publish)
    TextView tvBookPublish;
    @BindView(R.id.tv_book_publish_time)
    TextView tvBookPublishTime;
    private BookTypeBean bookType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_info;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        bookType = (BookTypeBean) bundle.getSerializable("book");
        tvBookName.setText(bookType.getName());
        tvBookAuthor.setText(bookType.getAuthor());
        tvBookPublish.setText(bookType.getPublishAddress());
        tvBookPublishTime.setText(PublicTools.dateToString(bookType.getPublishTime()));
    }
}