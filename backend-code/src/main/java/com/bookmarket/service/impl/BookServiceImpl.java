package com.bookmarket.service.impl;

import com.bookmarket.dto.request.BookReq;
import com.bookmarket.pojo.Book;
import com.bookmarket.service.BookService;
import com.bookmarket.mapper.BookMapper;
import com.bookmarket.utils.UserContext;
import com.bookmarket.vo.BookVO;
import com.bookmarket.vo.CategoryVO;
import com.bookmarket.vo.HotBooksVO;
import com.bookmarket.vo.PageVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author wetuh
* @description 针对表【book(书籍表)】的数据库操作Service实现
* @createDate 2026-01-25 21:33:47
*/
@Service
public class BookServiceImpl implements BookService{

    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public List<CategoryVO> getCategories() {
        List<CategoryVO> categoryVOList = bookMapper.listCategory();
        return categoryVOList;
    }

    @Override
    public List<HotBooksVO> listHotBooks(Integer limit) {

        return bookMapper.listHotBooks(limit);
    }

    @Override
    public PageVO getPage(BookReq bookReq) {
        PageHelper.startPage(bookReq.getPage(), bookReq.getPageSize());
        List<Book> bookList = bookMapper.listBooks(bookReq);
        Page<Book> page = (Page<Book>) bookList;
        return new PageVO(page.getResult(),page.getTotal(),page.getPageNum(),page.getPageSize());
    }

    @Override
    public BookVO getBookById(Long id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public List<BookVO> listSameCategoryBookById(Integer bookId, Integer limit) {
        return bookMapper.getSameCategoryBookById(bookId, limit);
    }

    @Override
    public PageVO getBooksByUserId(Long userId, Integer page, Integer pageSize,Integer status) {
        PageHelper.startPage(page,pageSize);
        List<Book> bookList = bookMapper.getBooksByUserId(userId,status);
        Page<Book> respage = (Page<Book>) bookList;
        return new PageVO(respage.getResult(),respage.getTotal(),respage.getPageNum(),respage.getPageSize());
    }

    @Override
    public Book sellBooks(Book book) {
        Long userId = UserContext.getCurrentUserId();
        book.setSellerId(userId);
        book.setStatus(1);
        Date date = new Date();
        book.setCreatedAt(date);
        book.setUpdatedAt(date);
        Long id = bookMapper.save(book);
        return book;
    }
}




