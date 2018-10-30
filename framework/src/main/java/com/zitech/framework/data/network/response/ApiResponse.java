package com.zitech.framework.data.network.response;


import com.google.gson.annotations.SerializedName;

/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package com.zitech.personalaccount.data.network.response
 * @Description: 响应基类
 * @date 2016/5/17 9:47
 */
public class ApiResponse<T> {

    /**
     * code : 0
     * msg : ok
     */
    public int status;
    public String msg;
    T data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
