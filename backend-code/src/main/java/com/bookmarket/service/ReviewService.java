package com.bookmarket.service;

import com.bookmarket.vo.PageVO;
import com.bookmarket.vo.StatisticVO;

/**
* @author wetuh
* @description 针对表【review(评价表)】的数据库操作Service
* @createDate 2026-01-27 14:32:58
*/
public interface ReviewService  {

    PageVO getBookReviews(Integer bookId, int page, int pageSize);

    StatisticVO getStatistics(Integer bookId);
}
