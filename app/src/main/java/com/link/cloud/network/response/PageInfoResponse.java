package com.link.cloud.network.response;

/**
 * 作者：qianlu on 2018/11/9 18:04
 * 邮箱：zar.l@qq.com
 */
public class PageInfoResponse {

    private int pageCount;
    private int count;
    private int pageSize;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
