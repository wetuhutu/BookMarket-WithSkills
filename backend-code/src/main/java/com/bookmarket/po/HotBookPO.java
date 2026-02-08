package com.bookmarket.po;

public class HotBookPO {
    private String title;
    private String author;

    @Override
    public String toString() {
        return "HotBookPO{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
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
}
