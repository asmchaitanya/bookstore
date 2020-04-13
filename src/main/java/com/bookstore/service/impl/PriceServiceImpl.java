package com.bookstore.service.impl;

import com.bookstore.entity.PriceEntity;
import com.bookstore.repository.PriceRepository;
import com.bookstore.service.PriceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PriceServiceImpl implements PriceService {

    @Resource
    private PriceRepository priceRepository;

    @Override
    public void setPrice(Long productId, Double price) {
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setProductId(productId);
        priceEntity.setMsrp(price);
        priceEntity.setNetPrice(price);

        priceRepository.save(priceEntity);
    }
}
