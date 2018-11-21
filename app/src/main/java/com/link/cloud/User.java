package com.link.cloud;


import com.link.cloud.network.response.RegisterResponse;
import com.zitech.framework.SP;

/**
 *
 */
public class User {

    private static final String DEVICEID = "deviceId";
    private static final String USESIGN = "useSign";
    private static final String NUMBERTYPE = "numberType";
    private static final String PASSWORD = "password";


    private SP sp;


    private String deviceId;
    private boolean useSign;
    private String password;
    private int numberType;

    public static User get() {
        return SixCatApplication.getInstance().getUser();
    }


    public User() {
        super();
        sp = new SP("USER_DATA");
        useSign = sp.getBoolean(USESIGN, false);
        deviceId = sp.getString(DEVICEID, "");
        numberType = sp.getInt(NUMBERTYPE, -1);
        password = sp.getString(PASSWORD, "666666");
    }


    public void storageInfo(RegisterResponse registerResponse) {
        setDeviceId(registerResponse.getDeviceId());
        setNumberType(registerResponse.getNumberType());
        setUseSign(registerResponse.isUseSign());
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        sp.putString(PASSWORD,password);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        sp.putString(DEVICEID, deviceId);
    }

    public boolean isUseSign() {
        return useSign;
    }

    public void setUseSign(boolean useSign) {
        this.useSign = useSign;
        sp.putBoolean(USESIGN, useSign);
    }

    public int getNumberType() {
        return numberType;
    }

    public void setNumberType(int numberType) {
        this.numberType = numberType;
        sp.putInt(NUMBERTYPE, numberType);
    }

    public void clear() {

        sp.remove(DEVICEID);
        deviceId = "";

        sp.remove(USESIGN);
        useSign = false;

        sp.remove(NUMBERTYPE);
        numberType = -1;


    }
}
