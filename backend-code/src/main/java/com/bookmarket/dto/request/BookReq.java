package com.bookmarket.dto.request;

public class BookReq {
    /**
     * 页码，默认1
     */
    private Integer page = 1;

    /**
     * 每页数量，默认12
     */
    private Integer pageSize = 12;
    /**
     * 分类筛选
     */
    private String category;

    /**
     * 最低价格
     */
    private Double priceMin;

    /**
     * 最高价格
     */
    private Double priceMax;

    /**
     * 新旧程度
     */
    private String condition;

    /**
     * 搜索关键词
     */
    private String keyword;

    /**
     * 排序方式
     */
    private String sortBy;

    public BookReq(Integer page, Integer pageSize,
                   String category, Double priceMin,
                   Double priceMax, String condition,
                   String keyword, String sortBy) {
        this.page = page;
        this.pageSize = pageSize;
        this.category = category;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.condition = condition;
        this.keyword = keyword;
        this.sortBy = sortBy;
    }

    @Override
    public String toString() {
        return "BookReq{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", category='" + category + '\'' +
                ", priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", condition='" + condition + '\'' +
                ", keyword='" + keyword + '\'' +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }


}
