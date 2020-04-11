package com.bookstore.service.impl;

import com.bookstore.entity.StockEntity;
import com.bookstore.exception.StoreException;
import com.bookstore.repository.StockRepository;
import com.bookstore.service.StockService;
import com.bookstore.vo.Stock;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Date;

@Component
@Transactional
public class StockServiceImpl implements StockService {

    @Resource
    private StockRepository stockRepository;

    @Resource
    private ConversionService conversionService;

    @Override
    public void initializeStock(Long productId, Integer quantity) {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setQuantity(quantity);
        stockEntity.setProductId(productId);
        stockEntity.setLastRefilled(new Date(System.currentTimeMillis()));

        stockRepository.save(stockEntity);
    }

    @Override
    public Stock findStockByProduct(Long productId) {
        StockEntity stockEntity = stockRepository.findByProductId(productId);

        if(null == stockEntity){
            throw new StoreException("No Stock base found ");
        }

        return conversionService.convert(stockEntity, Stock.class);
    }

}
