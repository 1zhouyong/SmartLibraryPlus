package com.example.smartlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.bean.BookCommentBean;
import com.example.smartlibrary.utils.PublicTools;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
 * 2022/4/27 : Create CommentExpandAdapter.java
 * -----------------------------------------------------------------
 */
public class CommentExpandAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<BookCommentBean> commentList;
    private String studyId;

    private IsLikeCallBack callBack;

    public void setCallBack(IsLikeCallBack callBack) {
        this.callBack = callBack;
    }

    public CommentExpandAdapter(Context context, List<BookCommentBean> commentList){
        this.context = context;
        this.commentList = commentList;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    @Override
    public int getGroupCount() {
        return commentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(commentList.get(groupPosition).getBookCommentReciveDTOList() == null){
            return 0;
        }else {
            return commentList.get(groupPosition).getBookCommentReciveDTOList().size() > 0 ?
                    commentList.get(groupPosition).getBookCommentReciveDTOList().size():0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commentList.get(groupPosition).getBookCommentReciveDTOList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder groupHolder;
        BookCommentBean groupComment = commentList.get(groupPosition);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.tv_content.setText(groupComment.getComment());
        groupHolder.tv_name.setText(studyId);
        groupHolder.tv_time.setText(PublicTools.getPassTime(groupComment.getUpdatedAt()));
        groupHolder.iv_like.setColorFilter(groupComment.isHasPraise() ? Color.parseColor("#aaaaaa"):Color.parseColor("#FF5C5C"));


        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.isLike(groupPosition);
            }
        });


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        BookCommentBean.BookCommentReciveDTOListBean commentReciveBean = commentList.get(groupPosition).getBookCommentReciveDTOList().get(childPosition);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout,parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.tv_name.setText(commentReciveBean.getUserName() + ":");
        childHolder.tv_content.setText(commentReciveBean.getComment());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder{
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private ImageView iv_like;
        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
        }
    }

    private class ChildHolder {
        private TextView tv_name, tv_content;
        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }

    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(BookCommentBean commentDetailBean){
        if(commentDetailBean!=null){

            commentList.add(commentDetailBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(BookCommentBean.BookCommentReciveDTOListBean replyDetailBean, int groupPosition){
        if(replyDetailBean!=null){
            Log.e("TAG", "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            if(commentList.get(groupPosition).getBookCommentReciveDTOList() != null ){
                commentList.get(groupPosition).getBookCommentReciveDTOList().add(replyDetailBean);
            }else {
                List<BookCommentBean.BookCommentReciveDTOListBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentList.get(groupPosition).setBookCommentReciveDTOList(replyList);
            }
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }


    public void setCommentPraise(int position){
        if (commentList != null){
            if (commentList.get(position).isHasPraise()){
                commentList.get(position).setCountPraise(commentList.get(position).getCountPraise()-1);
            }else {
                commentList.get(position).setCountPraise(commentList.get(position).getCountPraise()+1);
            }
            commentList.get(position).setHasPraise(!commentList.get(position).isHasPraise());
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("点赞失败");
        }

    }



    public interface IsLikeCallBack{
        void isLike(int position);
    }



}
