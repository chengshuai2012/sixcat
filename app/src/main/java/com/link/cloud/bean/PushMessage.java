package com.link.cloud.bean;


/**
 * Created by 30541 on 2018/3/13.
 */

public class PushMessage {
    String type;
    String uid;
    String shopId;
    String sendTime;
    String appid;
    String messageId;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getShopId() {
        return shopId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getUid() {
        return uid;
    }

    public String getAppid() {
        return appid;
    }
}
