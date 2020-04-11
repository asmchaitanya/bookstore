package com.bookstore.bookstore.service;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.BooksOrderResponse;
import com.bookstore.service.OrderService;
import com.bookstore.service.impl.OrderServiceImpl;
import com.bookstore.service.transaction.SecureBooksOrderTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private SecureBooksOrderTransaction secureBooksOrderTransaction;

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    @Test
    public void testOrderBook() {
        BooksOrderRequest request = TestDataUtil.getBooksOrderRequest();
        BooksOrderResponse expectedResponse = TestDataUtil.getBooksOrderResponse();

        Mockito.when(secureBooksOrderTransaction.executeOrder(request)).thenReturn(expectedResponse);

        BooksOrderResponse result = orderService.orderBook(request);

        Assertions.assertNotNull(result.getOrderId());
    }
}
