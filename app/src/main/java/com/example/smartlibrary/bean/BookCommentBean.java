package com.example.smartlibrary.bean;

import java.util.Date;
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
 * 2022/4/27 : Create BookCommentBean.java
 * -----------------------------------------------------------------
 */
public class BookCommentBean {
    /**
     * bookId : 1
     * id : 1
     * userName : 1904011011
     * comment : 哈哈哈……
     * updatedAt : 2020-05-01T12:07:44.000+0800
     * bookCommentReciveDTOList : [{"userName":"1905011077","comment":"哈什么哈， 哈哈哈\u2026\u2026","updatedAt":"2020-05-01T12:11:56.000+0800"}]
     * countPraise : 1
     * hasPraise : true
     */

    private int bookId;
    private int id;
    private String userName;
    private String comment;
    private String updatedAt;
    private int countPraise;
    private boolean hasPraise;
    private List<BookCommentReciveDTOListBean> bookCommentReciveDTOList;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCountPraise() {
        return countPraise;
    }

    public void setCountPraise(int countPraise) {
        this.countPraise = countPraise;
    }

    public boolean isHasPraise() {
        return hasPraise;
    }

    public void setHasPraise(boolean hasPraise) {
        this.hasPraise = hasPraise;
    }

    public List<BookCommentReciveDTOListBean> getBookCommentReciveDTOList() {
        return bookCommentReciveDTOList;
    }

    public void setBookCommentReciveDTOList(List<BookCommentReciveDTOListBean> bookCommentReciveDTOList) {
        this.bookCommentReciveDTOList = bookCommentReciveDTOList;
    }

    public static class BookCommentReciveDTOListBean {
        /**
         * userName : 1905011077
         * comment : 哈什么哈， 哈哈哈……
         * updatedAt : 2020-05-01T12:11:56.000+0800
         */

        private String userName;
        private String comment;
        private String updatedAt;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}

