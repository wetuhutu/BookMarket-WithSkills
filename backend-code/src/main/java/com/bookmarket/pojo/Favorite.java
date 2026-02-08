package com.bookmarket.pojo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;


/**
* 收藏表
* @TableName favorite
*/
public class Favorite implements Serializable {

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

    private Book book;

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", book=" + book +
                ", createdAt=" + createdAt +
                '}';
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    /**
    * 
    */
    @ApiModelProperty("")
    private Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
