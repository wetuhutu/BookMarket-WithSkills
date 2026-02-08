package com.bookmarket.pojo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Seller extends User{

    /**
     * 卖家等级
     */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("卖家等级")
    @Length(max= 20,message="编码长度不能超过20")
    private String sellerLevel;
    /**
     * 是否认证
     */
    @ApiModelProperty("是否认证")
    private Integer sellerIsVerified;
    /**
     * 卖家描述
     */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("卖家描述")
    @Length(max= 255,message="编码长度不能超过255")
    private String sellerDescription;
    /**
     *
     */
    @ApiModelProperty("")
    private BigDecimal sellerPositiverate;
    /**
     * 卖家评分
     */
    @ApiModelProperty("卖家评分")
    private BigDecimal sellerRating;

    @Override
    public String toString() {
        return "Seller{" +
                "sellerLevel='" + sellerLevel + '\'' +
                ", sellerIsVerified=" + sellerIsVerified +
                ", sellerDescription='" + sellerDescription + '\'' +
                ", sellerPositiverate=" + sellerPositiverate +
                ", sellerRating=" + sellerRating +
                ", sellerSalesCount=" + sellerSalesCount +
                '}';
    }

    public Integer getSellerSalesCount() {
        return sellerSalesCount;
    }

    public void setSellerSalesCount(Integer sellerSalesCount) {
        this.sellerSalesCount = sellerSalesCount;
    }

    public BigDecimal getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(BigDecimal sellerRating) {
        this.sellerRating = sellerRating;
    }

    public BigDecimal getSellerPositiverate() {
        return sellerPositiverate;
    }

    public void setSellerPositiverate(BigDecimal sellerPositiverate) {
        this.sellerPositiverate = sellerPositiverate;
    }

    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }

    public Integer getSellerIsVerified() {
        return sellerIsVerified;
    }

    public void setSellerIsVerified(Integer sellerIsVerified) {
        this.sellerIsVerified = sellerIsVerified;
    }

    public String getSellerLevel() {
        return sellerLevel;
    }

    public void setSellerLevel(String sellerLevel) {
        this.sellerLevel = sellerLevel;
    }

    /**
     * 销售数量
     */
    @ApiModelProperty("销售数量")
    private Integer sellerSalesCount;

}
