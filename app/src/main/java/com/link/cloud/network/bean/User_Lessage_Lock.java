package com.link.cloud.network.bean;

import com.google.gson.annotations.SerializedName;
import com.link.cloud.network.ResultResponse;


/**
 * 作者：qianlu on 2018/11/15 16:48
 * 邮箱：zar.l@qq.com
 */
public class User_Lessage_Lock extends ResultResponse {
    @SerializedName("uid")
    String uid;
    @SerializedName("name")
    String name;
    @SerializedName("numberType")
    int numberType;
    @SerializedName("numberValue")
    String numberValue;
    @SerializedName("cabinetNumber")
    String cabinetnumber;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberType(int numberType) {
        this.numberType = numberType;
    }

    public void setNumberValue(String numberValue) {
        this.numberValue = numberValue;
    }

    public void setCabinetnumber(String cabinetnumber) {
        this.cabinetnumber = cabinetnumber;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public int getNumberType() {
        return numberType;
    }

    public String getNumberValue() {
        return numberValue;
    }

    public String getCabinetnumber() {
        return cabinetnumber;
    }
}
