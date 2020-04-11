package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.controller.OrdersController;
import com.bookstore.exception.StoreException;
import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.BooksOrderResponse;
import com.bookstore.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrdersControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrdersController ordersController;

    @Test
    public void testOrderBook() {
        BooksOrderRequest request = TestDataUtil.getBooksOrderRequest();
        BooksOrderResponse expectedResponse = TestDataUtil.getBooksOrderResponse();

        Mockito.when(orderService.orderBook(request)).thenReturn(expectedResponse);
        BooksOrderResponse result = ordersController.orderBook(request);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getOrderId());
        Assertions.assertEquals(expectedResponse.getStatus(),result.getStatus());
    }

    @Test
    public void testOrderBookForException() {
        BooksOrderRequest request = TestDataUtil.getBooksOrderRequest();

        Mockito.when(orderService.orderBook(request)).thenThrow(new StoreException("No stock base found"));

        Assertions.assertThrows(StoreException.class,()->ordersController.orderBook(request));
    }

}
