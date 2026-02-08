package com.bookmarket.vo;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookVO {
    private Long id; // 书籍ID
    private String title; // 书籍标题
    private String author; // 作者
    private String isbn; // ISBN编号
    private String publisher; // 出版社
    private String publishDate; // 出版日期
    private Integer pages; // 页数
    private String categoryId; // 分类ID（对应SQL的category_id）
    private String condition; // 新旧程度（对应SQL的condition，保留原字段名）
    private Double price; // 售价
    private Double originalPrice; // 原价（对应SQL的original_price）
    private Integer stock; // 库存
    private String cover; // 封面图地址
    private Object images; // 书籍图片集（多图用逗号/分号分隔）
    private String description; // 书籍简介
    private LocalDateTime createdAt;

    // 卖家（用户）关联信息（从user表关联查询）
    private Long sellerId; // 卖家ID（对应SQL的sellerId）
    private String sellerName; // 卖家名称（对应SQL的sellerName）
    private String sellerLevel; // 卖家等级（对应SQL的sellerLevel）
    private Double sellerRating; // 卖家评分（对应SQL的sellerRating）
    private Boolean isVerified; // 卖家是否认证（对应SQL的isVerified）
    private Integer salesCount;

    @Override
    public String toString() {
        return "BookVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", pages=" + pages +
                ", categoryId='" + categoryId + '\'' +
                ", condition='" + condition + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", stock=" + stock +
                ", cover='" + cover + '\'' +
                ", images=" + images +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", sellerLevel='" + sellerLevel + '\'' +
                ", sellerRating=" + sellerRating +
                ", isVerified=" + isVerified +
                ", salesCount=" + salesCount +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(price);
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerLevel() {
        return sellerLevel;
    }

    public void setSellerLevel(String sellerLevel) {
        this.sellerLevel = sellerLevel;
    }

    public Double getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Double sellerRating) {
        this.sellerRating = sellerRating;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }
}
