package com.bookstore.bookstore.service;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.entity.PriceEntity;
import com.bookstore.repository.PriceRepository;
import com.bookstore.service.PriceService;
import com.bookstore.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService = new PriceServiceImpl();

    @Test
    public void testSetPrice() {

        PriceEntity savedEntity = getPriceEntity();

        Mockito.when(priceRepository.save(Mockito.any(PriceEntity.class))).thenReturn(savedEntity);

        priceService.setPrice(TestDataUtil.BOOK_ID, TestDataUtil.BOOK_PRICE);

        Mockito.verify(priceRepository, Mockito.times(1)).save(Mockito.any(PriceEntity.class));

    }

    private PriceEntity getPriceEntity() {
        PriceEntity priceEntity = new PriceEntity();

        priceEntity.setId(TestDataUtil.PRICE_INFO_ID);
        priceEntity.setNetPrice(TestDataUtil.BOOK_PRICE);
        priceEntity.setMsrp(TestDataUtil.BOOK_PRICE);
        priceEntity.setProductId(TestDataUtil.BOOK_ID);

        return priceEntity;
    }


}
