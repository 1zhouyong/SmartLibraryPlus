package com.example.smartlibrary.bean;

public class LoginBodyBean {

    private String password;
    private String studyId;

    public LoginBodyBean( String studyId,String password) {
        this.password = password;
        this.studyId = studyId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }
    public String getStudyId() {
        return studyId;
    }
}
