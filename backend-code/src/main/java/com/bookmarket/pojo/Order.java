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
* 订单表
* @TableName order
*/
public class Order implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 订单号
    */
    @NotBlank(message="[订单号]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("订单号")
    @Length(max= 32,message="编码长度不能超过32")
    private String orderNo;
    /**
    * 买家ID
    */
    @NotNull(message="[买家ID]不能为空")
    @ApiModelProperty("买家ID")
    private Long buyerId;
    /**
    * 卖家ID
    */
    @NotNull(message="[卖家ID]不能为空")
    @ApiModelProperty("卖家ID")
    private Long sellerId;

    @NotBlank(message="[用户名]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("用户名")
    @Length(max= 50,message="编码长度不能超过50")
    private String sellerName;
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
    * 单价
    */
    @NotNull(message="[单价]不能为空")
    @ApiModelProperty("单价")
    private BigDecimal price;
    /**
    * 总价
    */
    @NotNull(message="[总价]不能为空")
    @ApiModelProperty("总价")
    private BigDecimal totalPrice;
    /**
    * 订单状态
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("订单状态")
    @Length(max= 20,message="编码长度不能超过20")
    private String status;
    /**
    * 支付状态
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("支付状态")
    @Length(max= 20,message="编码长度不能超过20")
    private String paymentStatus;
    /**
    * 物流状态
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("物流状态")
    @Length(max= 20,message="编码长度不能超过20")
    private String shippingStatus;
    /**
    *
    */
    @ApiModelProperty("创建时间")
    private Date createdAt;
    /**
    * 支付时间
    */
    @ApiModelProperty("支付时间")
    private Date paidAt;
    /**
    * 发货时间
    */
    @ApiModelProperty("发货时间")
    private Date shippedAt;
    /**
    * 收货时间
    */
    @ApiModelProperty("收货时间")
    private Date receivedAt;

    @NotBlank(message="[书名]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("书名")
    @Length(max= 255,message="编码长度不能超过255")
    private String BookTitle;

    @NotBlank(message="[封面URL]不能为空")
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("封面URL")
    @Length(max= 500,message="编码长度不能超过500")
    private String BookCover;


    @Override
    public String toString() {
        return "Order{" +
                "  id=" + id +
                ", BookCover='" + BookCover + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", shippingStatus='" + shippingStatus + '\'' +
                ", createdAt=" + createdAt +
                ", paidAt=" + paidAt +
                ", shippedAt=" + shippedAt +
                ", receivedAt=" + receivedAt +
                ", BookTitle='" + BookTitle + '\'' +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getBookCover() {
        return BookCover;
    }

    public void setBookCover(String bookCover) {
        BookCover = bookCover;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public Date getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(Date shippedAt) {
        this.shippedAt = shippedAt;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }
}
