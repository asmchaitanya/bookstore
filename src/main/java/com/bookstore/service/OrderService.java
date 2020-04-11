package com.bookstore.service;

import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.BooksOrderResponse;

public interface OrderService {

    /**
     * Process books order request in accordance with available stock.
     *
     * @param s
     * @return BooksOrderResponse
     */
    BooksOrderResponse orderBook(BooksOrderRequest request);
}
