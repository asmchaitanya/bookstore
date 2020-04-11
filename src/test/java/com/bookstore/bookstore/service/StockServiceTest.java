package com.bookstore.bookstore.service;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.entity.StockEntity;
import com.bookstore.exception.StoreException;
import com.bookstore.repository.StockRepository;
import com.bookstore.service.StockService;
import com.bookstore.service.impl.StockServiceImpl;
import com.bookstore.vo.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private StockService stockService = new StockServiceImpl();

    @Test
    public void testInitializeStock() {
        StockEntity expectedResponse = getStockEntity();

        Mockito.when(stockRepository.save(Mockito.any())).thenReturn(expectedResponse);

        stockService.initializeStock(TestDataUtil.BOOK_ID, TestDataUtil.QUANTITY);

        Mockito.verify(stockRepository, Mockito.times(1)).save(Mockito.any(StockEntity.class));
    }

    @Test
    public void testFindByProductId() {
        StockEntity expectedResponse = getStockEntity();

        Mockito.when(stockRepository.findByProductId(TestDataUtil.BOOK_ID)).thenReturn(expectedResponse);
        Mockito.when(conversionService.convert(expectedResponse,Stock.class)).thenReturn(getStock());
        Stock stock = stockService.findStockByProduct(TestDataUtil.BOOK_ID);

        Assertions.assertNotNull(stock);
    }

    @Test
    public void testFindByProductIdWithInvalidBookId() {
        Long bookId = 0L;
        Mockito.when(stockRepository.findByProductId(bookId)).thenReturn(null);

        Assertions.assertThrows(StoreException.class,()->stockService.findStockByProduct(bookId));
    }

    private StockEntity getStockEntity() {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setQuantity(TestDataUtil.QUANTITY);
        stockEntity.setProductId(TestDataUtil.BOOK_ID);
        stockEntity.setId(3003L);

        return stockEntity;
    }

    private Stock getStock() {
        Stock stock = new Stock();
        stock.setQuantity(TestDataUtil.QUANTITY);
        stock.setProductId(TestDataUtil.BOOK_ID);

        return stock;
    }
}
