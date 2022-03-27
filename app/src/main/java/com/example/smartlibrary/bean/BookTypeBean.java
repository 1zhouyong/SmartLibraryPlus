package com.example.smartlibrary.bean;

import java.io.Serializable;
import java.util.Date;

public class BookTypeBean implements Serializable {
    /**
     * Copyright 2022 bejson.com
     */
    /**
     * Auto-generated: 2022-03-26 14:37:6
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

        private int id;
        private String bookNumber;
        private String name;
        private String descb;
        private String author;
        private String publishAddress;
        private Date publishTime;
        private String bookType;
        private String status;
        private int score;
        private String picPath;
        private String path;
        private String createdBy;
        private Date createdAt;
        private String updatedBy;
        private Date updatedAt;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setBookNumber(String bookNumber) {
            this.bookNumber = bookNumber;
        }

        public String getBookNumber() {
            return bookNumber;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setDescb(String descb) {
            this.descb = descb;
        }

        public String getDescb() {
            return descb;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return author;
        }

        public void setPublishAddress(String publishAddress) {
            this.publishAddress = publishAddress;
        }

        public String getPublishAddress() {
            return publishAddress;
        }

        public void setPublishTime(Date publishTime) {
            this.publishTime = publishTime;
        }

        public Date getPublishTime() {
            return publishTime;
        }

        public void setBookType(String bookType) {
            this.bookType = bookType;
        }

        public String getBookType() {
            return bookType;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }


        @Override
        public String toString() {
            return "JsonRootBean{" +
                    "id=" + id +
                    ", bookNumber='" + bookNumber + '\'' +
                    ", name='" + name + '\'' +
                    ", descb='" + descb + '\'' +
                    ", author='" + author + '\'' +
                    ", publishAddress='" + publishAddress + '\'' +
                    ", publishTime=" + publishTime +
                    ", bookType='" + bookType + '\'' +
                    ", status='" + status + '\'' +
                    ", score=" + score +
                    ", picPath='" + picPath + '\'' +
                    ", path='" + path + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedBy='" + updatedBy + '\'' +
                    ", updatedAt=" + updatedAt +
                    '}';
        }
    }
