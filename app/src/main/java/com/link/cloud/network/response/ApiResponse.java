package com.link.cloud.network.response;


/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package
 * @Description: 响应基类
 * @date 2016/5/17 9:47
 */
public class ApiResponse<T> {




    /**
     * code : 0
     * msg : ok
     */
    T data;
    private String status;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
