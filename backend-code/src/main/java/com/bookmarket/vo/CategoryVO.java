package com.bookmarket.vo;

public class CategoryVO {
    private String id;
    private String name;
    private String icon;
    private Integer count;

    public CategoryVO(String literature, String name, String s, int i) {
        this.id = literature;
        this.name = name;
        this.icon = s;
        this.count = i;
    }


    @Override
    public String toString() {
        return "CategoryVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", count=" + count +
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
