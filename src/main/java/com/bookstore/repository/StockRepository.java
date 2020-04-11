package com.bookstore.repository;

import com.bookstore.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

    StockEntity findByProductId(Long productId);
}
