package com.bookmarket.service;


import com.bookmarket.pojo.Favorite;
import com.bookmarket.vo.PageVO;
import org.springframework.transaction.annotation.Transactional;

/**
* @author wetuh
* @description 针对表【favorite(收藏表)】的数据库操作Service
* @createDate 2026-02-04 19:08:34
*/
public interface FavoriteService  {

    PageVO selectFavoriteListByUserId(Long userId, Integer page, Integer pageSize);

    void addFavorite(Favorite favorite);

    @Transactional
    void deleteFavorite(Long userId,Long bookId);
}
