package com.bookmarket.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 用户表
* @TableName user
*/
public abstract class User implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 用户名
    */
    @NotBlank(message="[用户名]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("用户名")
    @Length(max= 50,message="编码长度不能超过50")
    private String username;
    /**
    * 手机号
    */
    @NotBlank(message="[手机号]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("手机号")
    @Length(max= 20,message="编码长度不能超过20")
    private String phone;
    /**
    * 邮箱
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("邮箱")
    @Length(max= 100,message="编码长度不能超过100")
    private String email;
    /**
    * 密码（加密）
    */
    @NotBlank(message="[密码（加密）]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("密码（加密）")
    @Length(max= 255,message="编码长度不能超过255")
    private String password;
    /**
    * 头像URL
    */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("头像URL")
    @Length(max= 500,message="编码长度不能超过500")
    private String avatar;
    /**
    * 会员等级
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("会员等级")
    @Length(max= 20,message="编码长度不能超过20")
    private String level;
    /**
    * 积分
    */
    @ApiModelProperty("积分")
    private Integer points;
    /**
    * 是否卖家
    */
    @ApiModelProperty("是否卖家")
    private Integer isSeller;

    @ApiModelProperty("")
    private Date createdAt;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date updatedAt;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", level='" + level + '\'' +
                ", points=" + points +
                ", isSeller=" + isSeller +
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getIsSeller() {
        return isSeller;
    }

    public void setIsSeller(Integer isSeller) {
        this.isSeller = isSeller;
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
