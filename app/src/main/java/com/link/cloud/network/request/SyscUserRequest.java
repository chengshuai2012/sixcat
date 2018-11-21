package com.link.cloud.network.request;

/**
 * 作者：qianlu on 2018/11/8 17:09
 * 邮箱：zar.l@qq.com
 */
public class SyscUserRequest {
    private String deviceId;
    private String currPage;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCurrPage() {
        return currPage;
    }

    public void setCurrPage(String currPage) {
        this.currPage = currPage;
    }
}
