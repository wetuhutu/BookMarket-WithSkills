package com.bookmarket.service;

import com.bookmarket.dto.request.BookReq;
import com.bookmarket.pojo.Book;
import com.bookmarket.vo.BookVO;
import com.bookmarket.vo.CategoryVO;
import com.bookmarket.vo.HotBooksVO;
import com.bookmarket.vo.PageVO;

import java.util.List;

/**
* @author wetuh
* @description 针对表【book(书籍表)】的数据库操作Service
* @createDate 2026-01-25 21:33:47
*/
public interface BookService  {

    List<CategoryVO> getCategories();

    List<HotBooksVO> listHotBooks(Integer limit);

    PageVO getPage(BookReq bookReq);

    BookVO getBookById(Long id);

    List<BookVO> listSameCategoryBookById(Integer bookId, Integer limit);

    PageVO getBooksByUserId(Long userId, Integer page, Integer pageSize,Integer status);

    Book sellBooks(Book book);
}
