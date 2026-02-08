package com.bookmarket.dto;

public class RatingCountDTO {
    private Integer rating;
    private Long count;

    @Override
    public String toString() {
        return "RatingCountDTO{" +
                "rating=" + rating +
                ", count=" + count +
                '}';
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}