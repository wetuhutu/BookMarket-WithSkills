package com.bookmarket.service.impl;

import com.bookmarket.dto.RatingCountDTO;
import com.bookmarket.mapper.ReviewMapper;
import com.bookmarket.service.ReviewService;
import com.bookmarket.vo.PageVO;
import com.bookmarket.vo.StatisticVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author wetuh
* @description 针对表【review(评价表)】的数据库操作Service实现
* @createDate 2026-01-27 14:32:57
*/
@Service
public class ReviewServiceImpl implements ReviewService{

    ReviewMapper reviewMapper;

    @Autowired
    public  ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public PageVO getBookReviews(Integer bookId, int page, int pageSize){
        Page<Object> page1 = PageHelper.startPage(page, pageSize);
        reviewMapper.getReviewsByBookId(bookId);
        PageVO pageVO = new PageVO(page1.getResult(),page1.getTotal(),page1.getPageNum(),page1.getPageSize());
        return pageVO;
    }

    @Override
    public StatisticVO  getStatistics(Integer bookId){
        // 1. 从数据库获取评分分布
        List<RatingCountDTO> dtos = reviewMapper.getBookRatingStatistic(bookId);
        // 2. 初始化统计数据
        StatisticVO statisticVO = new StatisticVO();
        Map<Integer, Long> ratingMap = new HashMap<>();
        // 初始化 1-5 分的计数为 0
        for (int i = 1; i <= 5; i++) {
            ratingMap.put(i, 0L);
        }

        // 3. 计算总评价数和各评分的实际数量
        long totalCount = 0;
        double avgRating = 0.0;
        for (int i = 0; i < dtos.size(); i++) {
            totalCount += dtos.get(i).getCount();
            int rating = dtos.get(i).getRating();
            long count  = dtos.get(i).getCount();
            avgRating += rating * count;
            ratingMap.put(rating, count);
        }

        avgRating /= totalCount;

        // 5. 组装返回对象
        statisticVO.setRating(avgRating);
        statisticVO.setTotalCount(totalCount);
        statisticVO.setDistribution(ratingMap);
        return statisticVO;
    }

}
