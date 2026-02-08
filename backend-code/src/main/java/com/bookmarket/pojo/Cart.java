package com.bookmarket.pojo;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
* 购物车表
* @TableName cart
*/
public class Cart implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 用户ID
    */
    @NotNull(message="[用户ID]不能为空")
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
    * 书籍ID
    */
    @NotNull(message="[书籍ID]不能为空")
    @ApiModelProperty("书籍ID")
    private Long bookId;
    /**
    * 数量
    */
    @ApiModelProperty("数量")
    private Integer quantity;
    /**
    * 添加时的价格
    */
    @NotNull(message="[添加时的价格]不能为空")
    @ApiModelProperty("添加时的价格")
    private BigDecimal price;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date createdAt;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
