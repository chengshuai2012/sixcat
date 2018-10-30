package com.link.cloud.api.request;

/**
 * 作者：qianlu on 2018/10/25 11:36
 * 邮箱：zar.l@qq.com
 */
public class MemInfoRequest {

    private String deviceId;
    private String numberType;
    private String numberValue;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public void setNumberValue(String numberValue) {
        this.numberValue = numberValue;
    }
}
