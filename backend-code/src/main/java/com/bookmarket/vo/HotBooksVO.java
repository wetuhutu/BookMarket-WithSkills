package com.bookmarket.vo;

public class HotBooksVO {
    private long id;
    private String title;
    private String author;
    private String condition;
    private double price;
    private double originalPrice;
    private String cover;
    private String description;
    private String sellerName;
    private String sellerLevel;
    private double sellerRating;
    private long isVerified;

    @Override
    public String toString() {
        return "HotBooksVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", condition='" + condition + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", cover='" + cover + '\'' +
                ", description='" + description + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerLevel='" + sellerLevel + '\'' +
                ", sellerRating=" + sellerRating +
                ", isVerified=" + isVerified +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(double sellerRating) {
        this.sellerRating = sellerRating;
    }

    public long getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(long isVerified) {
        this.isVerified = isVerified;
    }
}
