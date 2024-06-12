package com.example.com.bookmanagerspringboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order { // 订单类
    private String orderId;
    private List<Book> cartList;
    private double totalAmount;
    private LocalDateTime orderTime;
}

