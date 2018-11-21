package com.link.cloud.network.request;

/**
 * 作者：qianlu on 2018/11/8 17:03
 * 邮箱：zar.l@qq.com
 */
public class GetDeviceIdRequest {

    /**
     * deviceType : 2
     * deviceTargetValue : 9041A45F8AF7FEAB0BC32B81B095FA38
     * key : 848ec6fa44ac6bae
     * datetime : 1541750028415
     * code : link
     * sign : 62484c08a4f132433e07f66e96dc9457
     */

    private int deviceType;
    private String deviceTargetValue;
    private String key;
    private String datetime;
    private String code;
    private String sign;

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTargetValue() {
        return deviceTargetValue;
    }

    public void setDeviceTargetValue(String deviceTargetValue) {
        this.deviceTargetValue = deviceTargetValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
