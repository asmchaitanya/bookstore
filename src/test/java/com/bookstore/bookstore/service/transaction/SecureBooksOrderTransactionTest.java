package com.bookstore.bookstore.service.transaction;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.constants.ApplicationContants;
import com.bookstore.entity.OrderEntity;
import com.bookstore.entity.StockEntity;
import com.bookstore.exception.StoreException;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.StockRepository;
import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.BooksOrderResponse;
import com.bookstore.service.transaction.SecureBooksOrderTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@ExtendWith(MockitoExtension.class)
public class SecureBooksOrderTransactionTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SecureBooksOrderTransaction secureBooksOrderTransaction;

    @Test
    public void testExecuteOrder() {
        BooksOrderRequest request = TestDataUtil.getBooksOrderRequest();
        request.setQuantity(1);
        StockEntity previousEntity = getStockEntity();
        StockEntity afterSaveEntity = getStockEntity();
        afterSaveEntity.setQuantity(previousEntity.getQuantity() - request.getQuantity());

        Mockito.when(stockRepository.findByProductId(request.getBookId())).thenReturn(previousEntity);
        Mockito.doNothing().when(entityManager).lock(previousEntity, LockModeType.PESSIMISTIC_WRITE);
        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class))).thenReturn(getOrderEntity());
        Mockito.when(stockRepository.save(previousEntity)).thenReturn(afterSaveEntity);


        BooksOrderResponse response = secureBooksOrderTransaction.executeOrder(request);

        Mockito.verify(stockRepository, Mockito.times(1)).save(previousEntity);
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(OrderEntity.class));
        Assertions.assertNotNull(response.getOrderId());

    }

    @Test
    public void testExecuteOrderWithLargeQuantityRequest() {
        BooksOrderRequest request = TestDataUtil.getBooksOrderRequest();
        request.setQuantity(11);
        StockEntity previousEntity = getStockEntity();

        Mockito.when(stockRepository.findByProductId(request.getBookId())).thenReturn(previousEntity);
        Mockito.doNothing().when(entityManager).lock(previousEntity, LockModeType.PESSIMISTIC_WRITE);

        Assertions.assertThrows(StoreException.class, () -> secureBooksOrderTransaction.executeOrder(request));
    }

    @Test
    public void testExecuteOrderForStockRefill() {
        BooksOrderRequest request = TestDataUtil.getBooksOrderRequest();
        request.setQuantity(1);
        StockEntity previousEntity = getStockEntity();
        previousEntity.setQuantity(0);
        StockEntity afterSaveEntity = getStockEntity();
        afterSaveEntity.setQuantity(ApplicationContants.REFILL_QUANTITY - request.getQuantity());

        Mockito.when(stockRepository.findByProductId(request.getBookId())).thenReturn(previousEntity);
        Mockito.doNothing().when(entityManager).lock(previousEntity, LockModeType.PESSIMISTIC_WRITE);
        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class))).thenReturn(getOrderEntity());
        Mockito.when(stockRepository.save(previousEntity)).thenReturn(afterSaveEntity);


        BooksOrderResponse response = secureBooksOrderTransaction.executeOrder(request);

        Mockito.verify(stockRepository, Mockito.times(1)).save(previousEntity);
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(OrderEntity.class));
        Assertions.assertNotNull(response.getOrderId());
    }

    private StockEntity getStockEntity() {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setQuantity(TestDataUtil.QUANTITY);
        stockEntity.setProductId(TestDataUtil.BOOK_ID);
        stockEntity.setId(3003L);

        return stockEntity;
    }

    private OrderEntity getOrderEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setEmail("test@mail.com");
        orderEntity.setProduct(TestDataUtil.BOOK_ID);
        orderEntity.setId(4004L);

        return orderEntity;
    }
}
