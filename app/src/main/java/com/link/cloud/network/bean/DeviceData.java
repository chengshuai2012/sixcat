package com.link.cloud.network.bean;

import com.google.gson.annotations.SerializedName;
import com.link.cloud.network.ResultResponse;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DeviceData extends ResultResponse {


    Devicedmessage deviceData;

    public void setDeviceData(Devicedmessage deviceData) {
        this.deviceData = deviceData;
    }

    public Devicedmessage getDeviceData() {
        return deviceData;
    }

}

