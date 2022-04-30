package com.example.smartlibrary.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.CommentExpandAdapter;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.BookCommentBean;
import com.example.smartlibrary.bean.BookTypeBean;
import com.example.smartlibrary.contract.BookInfoContract;
import com.example.smartlibrary.presenter.BookInfoPresenter;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.widget.CommentExpandableListView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class BookInfoActivity extends BaseMvpActivity<BookInfoPresenter> implements BookInfoContract.View, View.OnClickListener, ExpandableListView.OnGroupClickListener, CommentExpandAdapter.IsLikeCallBack {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_page_lv_comment)
    CommentExpandableListView expandableListView;
    @BindView(R.id.detail_page_do_comment)
    TextView bt_comment;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private BookInfoPresenter presenter;
    private BookTypeBean book;
    private String userId;
    private String studyId;
    private CommentExpandAdapter adapter;

    private List<BookCommentBean> commentList = new ArrayList<>();
    private String commentContent;
    private int clickPosition;
    private String replyContent;
    private int isLikePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

    private void initData() {
        setSupportActionBar(toolbar);
        // 给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle("图书简介");
        book = (BookTypeBean) getIntent().getSerializableExtra("book");
        userId = getIntent().getStringExtra("userId");
        studyId = getIntent().getStringExtra("studyId");
        initPresenter();
        bt_comment.setOnClickListener(this);
        expandableListView.setOnGroupClickListener(this);



    }

    private void initPresenter() {
        presenter = new BookInfoPresenter();
        presenter.attachView(this);

        getAllBookComment();



    }

    private void getAllBookComment() {
        HashMap<String, String> map = new HashMap<>();
        map.put("bookId",book.getId()+"");
        map.put("userId",userId);
        presenter.submitBookComment(MapToRequestBodyUtil.convertMapToBody(map));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_info;
    }

    @Override
    public void initView() {

    }


    @Override
    public void getCommentSuccess(List<BookCommentBean> commentBeanList) {
        commentList.clear();
        commentList.addAll(commentBeanList);
        adapter = new CommentExpandAdapter(this,commentBeanList);
        adapter.setStudyId(studyId);
        expandableListView.setAdapter(adapter);

        adapter.setCallBack(this);

    }

    @Override
    public void addBookCommentSuccess(boolean isSuccess) {
        if (isSuccess){
            showLongToast("评论成功");
            BookCommentBean commentBean = new BookCommentBean();
            commentBean.setComment(commentContent);
            commentBean.setUpdatedAt(new Date().toString());
            commentBean.setUserName(studyId);
            adapter.addTheCommentData(commentBean);
        }

    }

    @Override
    public void addBookCommentReplySuccess(boolean isSuccess) {
        if (isSuccess){
            showLongToast("回复成功");
            BookCommentBean.BookCommentReciveDTOListBean bookCommentReciveDTOListBean = new BookCommentBean.BookCommentReciveDTOListBean();
            bookCommentReciveDTOListBean.setComment(replyContent);
            bookCommentReciveDTOListBean.setUpdatedAt(new Date().toString());
            bookCommentReciveDTOListBean.setUserName(userId);
            adapter.addTheReplyData(bookCommentReciveDTOListBean , clickPosition);
        }
    }

    @Override
    public void setBookCommentPraiseSuccess(boolean isSuccess) {
        if (isSuccess){
            adapter.setCommentPraise(isLikePosition);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String errMessage) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_page_do_comment:
                showCommentDialog();
                break;

        }
    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    dialog.dismiss();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("bookId",book.getId()+"");
                    map.put("comment", commentContent);
                    map.put("userName",studyId);
                    presenter.addBookComment(MapToRequestBodyUtil.convertMapToBody(map));

                }else {
                    showLongToast("评论内容不能为空");
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        showReplyDialog(groupPosition);
        return true;
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
        clickPosition = position;
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentList.get(position).getUserName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){
                    HashMap map = new HashMap<>();
                    map.put("bookId",book.getId());
                    map.put("comment", replyContent);
                    map.put("parentId",commentList.get(position).getId());
                    map.put("userName",studyId);
                    presenter.addBookCommentReply(MapToRequestBodyUtil.convertMapToBody(map));
                }else {
                    showLongToast("回复内容不能为空");
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @Override
    public void isLike(int position) {
        isLikePosition = position;
        HashMap map = new HashMap<>();
        map.put("commentId",commentList.get(position).getId());
        map.put("userId",userId);
        presenter.setBookCommentPraise(MapToRequestBodyUtil.convertMapToBody(map));
    }
}