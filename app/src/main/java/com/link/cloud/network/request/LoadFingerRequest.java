package com.link.cloud.network.request;

/**
 * 作者：qianlu on 2018/11/8 16:56
 * 邮箱：zar.l@qq.com
 */
public class LoadFingerRequest {


    private String messageId;
    private String appid;
    private String shopId;
    private String deviceId;
    private String uid;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
