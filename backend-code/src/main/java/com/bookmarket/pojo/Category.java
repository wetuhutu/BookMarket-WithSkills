package com.bookmarket.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 分类表
* @TableName category
*/
public class Category implements Serializable {

    /**
    * 分类ID（英文标识）
    */
    @NotBlank(message="[分类ID（英文标识）]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("分类ID（英文标识）")
    @Length(max= 50,message="编码长度不能超过50")
    private String id;
    /**
    * 分类名称
    */
    @NotBlank(message="[分类名称]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("分类名称")
    @Length(max= 50,message="编码长度不能超过50")
    private String name;
    /**
    * 分类图标（emoji）
    */
    @NotBlank(message="[分类图标（emoji）]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @ApiModelProperty("分类图标（emoji）")
    @Length(max= 10,message="编码长度不能超过10")
    private String icon;
    /**
    * 分类描述
    */
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("分类描述")
    @Length(max= 200,message="编码长度不能超过200")
    private String description;
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
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
