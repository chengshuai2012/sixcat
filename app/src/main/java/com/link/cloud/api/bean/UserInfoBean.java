package com.link.cloud.api.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：qianlu on 2018/10/25 11:22
 * 邮箱：zar.l@qq.com
 */
public class UserInfoBean {
    private String uid;
    private int sex;
    private String phone;
    private String name;
    private String img;
    private String userType;
    private String feature;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
