package com.example.smartlibrary.bean;

import java.util.Date;

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
 * 2022/4/21 : Create MyLectureBean.java
 * -----------------------------------------------------------------
 */
public class MyLectureBean {
    /**
     * id : 1
     * name : 大学生心理素质讲座
     * descb : 大学生心理素质教育目的是……
     * teacher : 张三
     * location : 1栋-201
     * beginTime : 2020-04-18T13:03:42.000+0800
     * endTime : 2020-04-18T15:03:46.000+0800
     * orderNumber : 1
     * sumNumber : 90
     */

    private int id;
    private String name;
    private String descb;
    private String teacher;
    private String location;
    private Date beginTime;
    private Date endTime;
    private int orderNumber;
    private int sumNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescb() {
        return descb;
    }

    public void setDescb(String descb) {
        this.descb = descb;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getSumNumber() {
        return sumNumber;
    }

    public void setSumNumber(int sumNumber) {
        this.sumNumber = sumNumber;
    }
}