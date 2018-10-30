package com.link.cloud.api.response;

import java.util.List;

public class PageResponse<T> {


    /**
     * pageNum : 1
     * pageSize : 10
     * size : 1
     * total : 1
     * pages : 1
     * rows : [{"joinTime":"2018-07-02 15:08:31","name":"张三","level":2,"mobile":"15685458754"}]
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int total;
    private int pages;


    private List<T> rows;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public static class RowsBean {

    }
}
