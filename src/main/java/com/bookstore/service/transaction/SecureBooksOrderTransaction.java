package com.bookstore.service.transaction;

import com.bookstore.constants.ApplicationContants;
import com.bookstore.entity.OrderEntity;
import com.bookstore.entity.StockEntity;
import com.bookstore.enums.OrderStatus;
import com.bookstore.exception.StoreException;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.StockRepository;
import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.BooksOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SecureBooksOrderTransaction {

    private static Logger LOGGER = LoggerFactory.getLogger(SecureBooksOrderTransaction.class);

    @Resource
    private EntityManager entityManager;

    @Resource
    private StockRepository stockRepository;

    @Resource
    private OrderRepository orderRepository;

    public BooksOrderResponse executeOrder(BooksOrderRequest request) {
        StockEntity currentStock = stockRepository.findByProductId(request.getBookId());

        entityManager.lock(currentStock, LockModeType.PESSIMISTIC_WRITE);

        currentStock = stockRepository.findByProductId(request.getBookId());

        if (currentStock.getQuantity() != 0 && currentStock.getQuantity() < request.getQuantity()) {
            throw new StoreException("Request quantity exceed current stock");
        }

        if (currentStock.getQuantity() == 0) {
            currentStock.setQuantity(ApplicationContants.REFILL_QUANTITY);
            currentStock.setLastRefilled(new Date(System.currentTimeMillis()));
        }

        AtomicInteger availableQuantity = new AtomicInteger(currentStock.getQuantity());
        availableQuantity.set(currentStock.getQuantity() - request.getQuantity());
        currentStock.setQuantity(availableQuantity.intValue());

        LOGGER.info("Quantity of product {} decremented to {}", currentStock.getProductId(), currentStock.getQuantity());

        StockEntity stock = stockRepository.save(currentStock);
        OrderEntity result = orderRepository.save(getNewOrderEntity(request));

        LOGGER.info("Order {} successfully placed", result.getId());

        BooksOrderResponse booksOrderResponse = buildOrderResponse(result);
        booksOrderResponse.setEmail(request.getEmail());

        return booksOrderResponse;
    }

    private OrderEntity getNewOrderEntity(BooksOrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(OrderStatus.CONFIRMED);
        orderEntity.setProduct(orderRequest.getBookId());
        orderEntity.setEmail(orderRequest.getEmail());
        orderEntity.setCreatedAt(new Date(System.currentTimeMillis()));
        orderEntity.setUpdatedAt(new Date(System.currentTimeMillis()));
        return orderEntity;
    }

    private BooksOrderResponse buildOrderResponse(OrderEntity result) {
        BooksOrderResponse booksOrderResponse = new BooksOrderResponse();
        booksOrderResponse.setOrderId(result.getId());
        booksOrderResponse.setCreatedDate(result.getCreatedAt());
        booksOrderResponse.setStatus(result.getStatus());

        return booksOrderResponse;
    }
}
