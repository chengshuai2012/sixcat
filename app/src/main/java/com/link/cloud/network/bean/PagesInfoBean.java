package com.link.cloud.network.bean;

/**
 * 作者：qianlu on 2018/11/8 16:42
 * 邮箱：zar.l@qq.com
 */
public class PagesInfoBean {


    private DataBean data;
    private String msg;
    private int status;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
