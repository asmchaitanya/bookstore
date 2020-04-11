package com.bookstore.service.impl;

import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.BooksOrderResponse;
import com.bookstore.service.OrderService;
import com.bookstore.service.transaction.SecureBooksOrderTransaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class OrderServiceImpl implements OrderService {

    @Resource
    private SecureBooksOrderTransaction secureBooksOrderTransaction;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BooksOrderResponse orderBook(BooksOrderRequest orderRequest) {
        return secureBooksOrderTransaction.executeOrder(orderRequest);
    }

}
