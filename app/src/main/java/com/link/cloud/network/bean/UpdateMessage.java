package com.link.cloud.network.bean;

import com.link.cloud.network.ResultResponse;


/**
 * 作者：qianlu on 2018/11/15 16:48
 * 邮箱：zar.l@qq.com
 */
public class UpdateMessage extends ResultResponse {
    String fileName;
    String version;
    String url;
    String remark;
    String createTime;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }


}
