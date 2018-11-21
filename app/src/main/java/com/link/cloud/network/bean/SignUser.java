package com.link.cloud.network.bean;

import io.realm.RealmObject;


/**
 * 作者：qianlu on 2018/11/15 16:48
 * 邮箱：zar.l@qq.com
 */
public class SignUser extends RealmObject {
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
