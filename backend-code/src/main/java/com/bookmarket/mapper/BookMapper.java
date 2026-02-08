package com.bookmarket.mapper;

import com.bookmarket.dto.request.BookReq;
import com.bookmarket.pojo.Book;
import com.bookmarket.vo.BookVO;
import com.bookmarket.vo.CategoryVO;
import com.bookmarket.vo.HotBooksVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
* @author wetuh
* @description 针对表【book(书籍表)】的数据库操作Mapper
* @createDate 2026-01-25 21:33:47
* @Entity com.bookmarket.pojo.Book
*/
@Mapper
public interface BookMapper {

    List<CategoryVO> listCategory();

    List<HotBooksVO> listHotBooks(Integer limit);

    List<Book> listBooks(BookReq bookReq);

    BookVO getBookById(Long id);

    List<BookVO> getSameCategoryBookById(Integer bookId, Integer limit);

    List<Book> getBooksByUserId(Long userId,Integer status);

    Long save(Book book);
}




