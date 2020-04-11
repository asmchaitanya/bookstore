package com.bookstore.service;

import com.bookstore.vo.Stock;

public interface StockService {

    /**
     * Intialize stock of newly added books in store.
     * @param productId
     * @param quantity
     */
    void initializeStock(Long productId, Integer quantity);

    /**
     * Return stock details of the provided book.
     * @param productId
     * @return Stock
     */
    Stock findStockByProduct(Long productId);

}
