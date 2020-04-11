package com.bookstore.converter;

import com.bookstore.entity.StockEntity;
import com.bookstore.vo.Stock;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StockEntityToStock implements Converter<StockEntity, Stock> {

    @Override
    public Stock convert(StockEntity stockEntity) {
        Stock stock = new Stock();

        stock.setProductId(stockEntity.getProductId());
        stock.setQuantity(stockEntity.getQuantity());

        return stock;
    }
}
