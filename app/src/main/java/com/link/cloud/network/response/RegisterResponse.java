package com.link.cloud.network.response;

/**
 * 作者：qianlu on 2018/11/9 17:24
 * 邮箱：zar.l@qq.com
 */
public class RegisterResponse {


    /**
     * useSign : true
     * deviceId : 4000A0I9YZC
     * numberType : 1
     */

    private boolean useSign;
    private String deviceId;
    private int numberType;

    public boolean isUseSign() {
        return useSign;
    }

    public void setUseSign(boolean useSign) {
        this.useSign = useSign;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getNumberType() {
        return numberType;
    }

    public void setNumberType(int numberType) {
        this.numberType = numberType;
    }
}
