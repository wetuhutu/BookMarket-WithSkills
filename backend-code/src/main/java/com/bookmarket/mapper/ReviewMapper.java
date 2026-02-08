package com.bookmarket.mapper;

import com.bookmarket.dto.RatingCountDTO;
import com.bookmarket.pojo.Review;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @author wetuh
* @description 针对表【review(评价表)】的数据库操作Mapper
* @createDate 2026-01-27 14:32:57
* @Entity com.bookmarket.pojo.Review
*/
@Mapper
public interface ReviewMapper {

    List<Review> getReviewsByBookId(Integer BookId);

    @MapKey("rating")
    List<RatingCountDTO> getBookRatingStatistic(Integer BookId);

}
