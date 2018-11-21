package com.link.cloud.network.request;

/**
 * 作者：qianlu on 2018/11/8 17:07
 * 邮箱：zar.l@qq.com
 */
public class ValidationQrCodeRequest {
    private String deviceId;
    private String qrCodeStr;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setQrCodeStr(String qrCodeStr) {
        this.qrCodeStr = qrCodeStr;
    }
}
