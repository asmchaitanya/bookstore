package com.bookstore.controller;

import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.BooksOrderResponse;
import com.bookstore.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Apis for managing books order.
 */
@RestController
@RequestMapping(value = "/api/v1/orders")
@Validated
public class OrdersController {

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public BooksOrderResponse orderBook(@Valid @RequestBody BooksOrderRequest booksOrderRequest) {
        return orderService.orderBook(booksOrderRequest);
    }

}
