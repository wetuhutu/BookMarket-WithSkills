package com.bookmarket.service.impl;

import com.bookmarket.mapper.FavoriteMapper;
import com.bookmarket.pojo.Favorite;
import com.bookmarket.service.FavoriteService;
import com.bookmarket.utils.UserContext;
import com.bookmarket.vo.PageVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author wetuh
* @description 针对表【favorite(收藏表)】的数据库操作Service实现
* @createDate 2026-02-04 19:08:34
*/
@Service
public class FavoriteServiceImpl implements FavoriteService{

    private final FavoriteMapper favoriteMapper;

    public FavoriteServiceImpl(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public PageVO selectFavoriteListByUserId(Long userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Favorite> favoriteList=favoriteMapper.selectFavoriteListByUserId(userId);
        Page<Favorite>  favoritePage= (Page<Favorite>) favoriteList;
        return new PageVO(favoritePage.getResult(),favoritePage.getTotal(),favoritePage.getPageNum(),favoritePage.getPageSize());
    }

    @Transactional
    @Override
    public void addFavorite(Favorite favorite){
        try {
            favorite.setUserId(UserContext.getCurrentUserId());
            favoriteMapper.insertFavorite(favorite);
        } catch (Exception e) {
            throw new RuntimeException("添加收藏失败 ："+e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteFavorite(Long userId,Long bookId){
        Boolean deleted = favoriteMapper.delete(userId, bookId);
        if(deleted){
            return;
        }
        throw new RuntimeException("取消书籍收藏失败");
    }
}
