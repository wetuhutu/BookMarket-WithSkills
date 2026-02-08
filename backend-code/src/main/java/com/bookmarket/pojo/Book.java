package com.bookmarket.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 书籍表
* @TableName book
*/
public class Book implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 书名
    */
    @NotBlank(message="[书名]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("书名")
    @Length(max= 255,message="编码长度不能超过255")
    private String title;
    /**
    * 作者
    */
    @NotBlank(message="[作者]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("作者")
    @Length(max= 100,message="编码长度不能超过100")
    private String author;
    /**
    * ISBN
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("ISBN")
    @Length(max= 20,message="编码长度不能超过20")
    private String isbn;
    /**
    * 出版社
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("出版社")
    @Length(max= 100,message="编码长度不能超过100")
    private String publisher;
    /**
    * 出版时间
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("出版时间")
    @Length(max= 20,message="编码长度不能超过20")
    private String publishDate;
    /**
    * 页数
    */
    @ApiModelProperty("页数")
    private Integer pages;
    /**
    * 分类
    */
    @NotBlank(message="[分类]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("分类")
    @Length(max= 50,message="编码长度不能超过50")
    private String categoryId;
    /**
    * 新旧程度
    */
    @NotBlank(message="[新旧程度]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("新旧程度")
    @Length(max= 20,message="编码长度不能超过20")
    private String condition;
    /**
    * 售价
    */
    @NotNull(message="[售价]不能为空")
    @ApiModelProperty("售价")
    private BigDecimal price;
    /**
    * 原价
    */
    @ApiModelProperty("原价")
    private BigDecimal originalPrice;
    /**
    * 库存
    */
    @ApiModelProperty("库存")
    private Integer stock;
    /**
    * 封面URL
    */
    @NotBlank(message="[封面URL]不能为空")
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("封面URL")
    @Length(max= 500,message="编码长度不能超过500")
    private String cover;
    /**
    * 图片数组
    */
    @ApiModelProperty("图片数组")
    private Object images;
    /**
    * 描述
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("描述")
    @Length(max= -1,message="编码长度不能超过-1")
    private String description;
    /**
    * 卖家ID
    */
    @NotNull(message="[卖家ID]不能为空")
    @ApiModelProperty("卖家ID")
    private Long sellerId;

    @ApiModelProperty("状态：1-在售，0-下架")
    private Integer status;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date createdAt;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date updatedAt;

    @Override
    public String toString() {
        return "Book{" +
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
                ", sellerId=" + sellerId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
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
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
