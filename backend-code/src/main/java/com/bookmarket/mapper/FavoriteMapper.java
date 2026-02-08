package com.bookmarket.mapper;


import com.bookmarket.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
* @author wetuh
* @description 针对表【favorite(收藏表)】的数据库操作Mapper
* @createDate 2026-02-04 19:08:34
* @Entity com.bookmarket.pojo.Favorite
*/
@Mapper
public interface FavoriteMapper  {

    List<Favorite> selectFavoriteListByUserId(Long userId);


    void insertFavorite(Favorite favorite);

    Boolean delete(Long userId,Long bookId);
}
