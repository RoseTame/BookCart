package com.example.com.bookmanagerspringboot.controller;

import com.example.com.bookmanagerspringboot.bean.Book;
import com.example.com.bookmanagerspringboot.bean.Order;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.com.bookmanagerspringboot.controller.BookController.cartList;

@RestController
@RequestMapping("order")
public class OrderController {
    private List<Order> orderList = new ArrayList<>();

    @GetMapping("/list")
    public List<Order> getOrderList() {
        return orderList;
    }

    // 提交订单
    @PostMapping("/submit")
    public Order submitOrder(@RequestBody List<Book> books) {
        // 生成订单号
        String orderId = UUID.randomUUID().toString();
        // 计算总金额
        double totalAmount = calculateTotalAmount(books);
        // 获取当前时间
        LocalDateTime orderTime = LocalDateTime.now();
        // 创建订单对象
        Order order = new Order(orderId, books, totalAmount, orderTime);
        // 将订单添加到订单列表
        orderList.add(order);
        //清空购物车
        cartList.clear();
        return order;
    }

    // 根据订单 id 查询订单
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null; // 没有找到对应的订单
    }

    // 删除订单
    @DeleteMapping("/{orderId}")
    public boolean deleteOrder(@PathVariable String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                orderList.remove(order);
                return true;
            }
        }
        return false; // 没有找到对应的订单
    }

    // 计算总金额
    private double calculateTotalAmount(List<Book> books) {
        double totalAmount = 0;
        for (Book book : books) {
            totalAmount += book.getPrice()*book.getCount();
        }
        return totalAmount;
    }


}
