package com.bookmarket.controller;

import com.bookmarket.dto.response.Result;
import com.bookmarket.pojo.Book;
import com.bookmarket.pojo.Favorite;
import com.bookmarket.pojo.Seller;
import com.bookmarket.service.BookService;
import com.bookmarket.service.FavoriteService;
import com.bookmarket.service.OrderService;
import com.bookmarket.service.UserService;
import com.bookmarket.utils.UserContext;
import com.bookmarket.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;
    private final FavoriteService favoriteService ;

    @Autowired
    public UserController(UserService userService, BookService bookService, OrderService orderService, FavoriteService favoriteService) {
        this.userService = userService;
        this.bookService = bookService;
        this.orderService = orderService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("profile")
    public Result getProfile(){
        String username = UserContext.getCurrentUsername();
        Seller userInfo = userService.getOneSeller(username);
        return Result.success(userInfo);
    }

    @PutMapping("profile")
    public Result putProfile(@RequestBody Seller seller){
        String username = UserContext.getCurrentUsername();
        Seller updateSeller = userService.updateSeller(seller,username);
        return Result.success(updateSeller);
    }

    @GetMapping("/books")
    public Result getBooks(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer status

    ) {
        try {
            Long userId = UserContext.getCurrentUserId();
            PageVO pageVO = bookService.getBooksByUserId(userId, page, pageSize,status);
            return Result.success(pageVO);
        } catch (Exception e) {
            throw new RuntimeException("获取用户书籍失败"+e.getMessage());
        }
    }

    @GetMapping("/orders")
    public Result getOrders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status
    ){
        try {
            Long userId = UserContext.getCurrentUserId();
            PageVO pageVO = orderService.getMyOrders(page,pageSize,userId,status);
            return Result.success(pageVO);
        } catch (Exception e) {
            throw new RuntimeException("获取用户订单失败"+e.getMessage());
        }
    }

    @GetMapping("/favorites")
    public Result getFavorites(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        PageVO pageVO = favoriteService.selectFavoriteListByUserId(UserContext.getCurrentUserId(), page, pageSize);
        return Result.success(pageVO);
    }

    @PostMapping("/favorites")
    public Result put(@RequestBody Favorite favorite) {
        favoriteService.addFavorite(favorite);
        return Result.success("收藏成功");
    }

    @DeleteMapping("/favorites/{bookId}")
    public Result delete(@PathVariable Long bookId) {
        favoriteService.deleteFavorite(UserContext.getCurrentUserId(),bookId);
        return Result.success("取消收藏成功");
    }

    @PostMapping("/books")
    public Result sellBooks(@RequestBody Book book) {
        Book soldBooks = bookService.sellBooks(book);
        return Result.success(soldBooks);
    }

}
