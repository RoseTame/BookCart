package com.example.com.bookmanagerspringboot.controller;

import com.example.com.bookmanagerspringboot.bean.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    static List<Book> bookList = new ArrayList<>();
    static List<Book> cartList = new ArrayList<>();
    static {
        bookList.add(new Book(1,"红楼梦","曹雪芹",60,true,0));
        bookList.add(new Book(2,"三国演义","罗贯中",55,true,0));
        bookList.add(new Book(3,"水浒传","曹雪芹",50,true,0));
        bookList.add(new Book(4,"西游记","吴承恩",55,true,0));
    }

    @GetMapping("/book/list")
    public List<Book> getBookList() {
        return bookList;
    }

    @GetMapping("/cart/list")
    public List<Book> getCartList() {
        return cartList;
    }

    @PostMapping("cart/add")
    public String addToCart(@RequestBody Map<String, Integer> payload) {
        int id = payload.get("id");
        for (Book b : bookList) {
            if (b.getId() == id) {
                boolean found = false;
                for (Book cartBook : cartList) {
                    if (cartBook.getId() == id) {
                        cartBook.setCount(cartBook.getCount() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    b.setCount(1); // 添加新书籍到购物车时，设置初始数量为 1
                    cartList.add(b);
                }
                return "添加成功";
            }
        }
        return "添加失败";
    }

    @DeleteMapping("cart/delete/{id}")
    public boolean delete(@PathVariable int id) {
        boolean removed = false;
        Iterator<Book> iterator = cartList.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == id) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        return removed;
    }
}
