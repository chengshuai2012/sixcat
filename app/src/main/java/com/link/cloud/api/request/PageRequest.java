package com.link.cloud.api.request;

public class PageRequest {
//
//    token（必填）
//    pageNum当前页
//    pageSize 每页数量

    private String token;
    private int pageNum;
    private int pageSize;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
