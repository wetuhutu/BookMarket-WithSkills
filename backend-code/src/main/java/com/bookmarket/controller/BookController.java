package com.bookmarket.controller;

import com.bookmarket.dto.request.BookReq;
import com.bookmarket.dto.response.Result;
import com.bookmarket.pojo.Seller;
import com.bookmarket.service.BookService;
import com.bookmarket.service.ReviewService;
import com.bookmarket.service.UserService;
import com.bookmarket.utils.RedisUtil;
import com.bookmarket.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final RedisUtil redisUtil;



    @Autowired
    public BookController(BookService bookService,
                          UserService userService,
                          ReviewService reviewService, RedisUtil redisUtil
    ) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.redisUtil = redisUtil;
    }

    @GetMapping("/categories")
    public Result getCategories() {
        List<CategoryVO> categories = bookService.getCategories();
        return Result.success(categories);
    }

    @GetMapping("/hot")
    public Result getHotBooks(@RequestParam(defaultValue = "4") Integer limit) {
        List<HotBooksVO> hotBooks = bookService.listHotBooks(limit);
        return Result.success(hotBooks);
    }

    @GetMapping
    public Result getPageOfBooks(
            @RequestParam(defaultValue = "1" ) Integer page,
            @RequestParam(defaultValue = "12") Integer pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortBy
            ) {
        BookReq bookReq = new BookReq(page, pageSize, category
                , priceMin, priceMax, condition, keyword, sortBy);
        PageVO pageVO = bookService.getPage(bookReq);
        return Result.success(pageVO);
    }

    @GetMapping("/{id}")
    public Result getOneBook(@PathVariable("id") Long id) {
        String key = "book:hot";
        redisUtil.increaseScore(key,id.toString(), 1L);
        BookVO bookVO = bookService.getBookById(id);
        return Result.success(bookVO);
    }

    @GetMapping("/hot/top")
    public Result getTopBook(){
        String key = "book:hot";
        Set<String> top = redisUtil.getRevMemberRange(key, 0L, 0L);
        String hotId = top.iterator().next();
        BookVO bookById = bookService.getBookById(Long.valueOf(hotId));
        return Result.success(bookById);
    }

    @GetMapping("/{id}/seller")
    public Result getOneSeller(@PathVariable("id") Long sellerId) {
        Seller oneSeller = userService.getOneSeller(sellerId);
        return Result.success(oneSeller);
    }

    @GetMapping("/{id}/reviews")
    public Result getBookReviews(@PathVariable("id") Integer BookId,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        PageVO bookReviews = reviewService.getBookReviews(BookId, page, pageSize);
        return Result.success(bookReviews);
    }

    @GetMapping("/{id}/reviews/statistics")
    public Result getBookReviewsStatistics(@PathVariable("id") Integer BookId) {
        StatisticVO statistics = reviewService.getStatistics(BookId);
        return Result.success(statistics);
    }

    @GetMapping("/{id}/related")
    public Result getBookRelated(@PathVariable("id") Integer BookId,
                                 @RequestParam(defaultValue = "4") Integer limit) {
        List<BookVO> bookVOS = bookService.listSameCategoryBookById(BookId, limit);
        return Result.success(bookVOS);
    }

}
