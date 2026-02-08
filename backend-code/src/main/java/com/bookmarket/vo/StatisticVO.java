package com.bookmarket.vo;

import java.util.Map;

public class StatisticVO {
    Double  rating;
    Long totalCount;
    Map<Integer, Long> distribution;

    @Override
    public String toString() {
        return "StatisticVO{" +
                "rating=" + rating +
                ", totalCount=" + totalCount +
                ", distribution=" + distribution +
                '}';
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Map<Integer, Long> getDistribution() {
        return distribution;
    }

    public void setDistribution(Map<Integer, Long> distribution) {
        this.distribution = distribution;
    }
}
