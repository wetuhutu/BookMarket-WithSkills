package com.bookmarket.vo;

import java.util.List;

public class PageVO {
    private List list;
    private Long total;
    private int page;
    private int pageSize;

    public PageVO(List list, Long total, int page, int pageSize) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "list=" + list +
                ", total=" + total +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
