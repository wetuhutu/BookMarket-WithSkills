package com.bookmarket.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 评价表
* @TableName review
*/
public class Review implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 书籍ID
    */
    @NotNull(message="[书籍ID]不能为空")
    @ApiModelProperty("书籍ID")
    private Long bookId;
    /**
    * 评价用户ID
    */
    @NotNull(message="[评价用户ID]不能为空")
    @ApiModelProperty("评价用户ID")
    private Long userId;
    /**
    * 评价用户名称
    */
    @NotBlank(message="[评价用户名称]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("评价用户名称")
    @Length(max= 50,message="编码长度不能超过50")
    private String userName;
    /**
    * 评价用户头像
    */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("评价用户头像")
    @Length(max= 500,message="编码长度不能超过500")
    private String userAvatar;
    /**
    * 评分（1-5）
    */
    @NotNull(message="[评分（1-5）]不能为空")
    @ApiModelProperty("评分（1-5）")
    private Integer rating;
    /**
    * 评价内容
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("评价内容")
    @Length(max= -1,message="编码长度不能超过-1")
    private String content;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date createdAt;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", rating=" + rating +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
