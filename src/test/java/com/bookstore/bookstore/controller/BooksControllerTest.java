package com.bookstore.bookstore.controller;


import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.controller.BooksController;
import com.bookstore.exception.StoreException;
import com.bookstore.request.AddBookRequest;
import com.bookstore.request.BookSearchRequest;
import com.bookstore.response.AddBookResponse;
import com.bookstore.response.BookSearchResponse;
import com.bookstore.service.BooksService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BooksControllerTest {

    @Mock
    private BooksService booksService;

    @InjectMocks
    private BooksController booksController;

    @Test
    public void testAddBook() {
        AddBookRequest request = TestDataUtil.getAddBookRequest();
        AddBookResponse expectedResponse = TestDataUtil.getAddBookResponse();

        Mockito.when(booksService.addBook(request)).thenReturn(expectedResponse);

        AddBookResponse result = booksController.addBook(request);

        Assertions.assertEquals(expectedResponse.getIsbn(), result.getIsbn());
        Assertions.assertEquals(expectedResponse.getTitle(), result.getTitle());
    }

    @Test
    public void testAddBookForException() {
        AddBookRequest request = TestDataUtil.getAddBookRequest();

        Mockito.when(booksService.addBook(request)).thenThrow(new StoreException("Book already exists with give isbn."));

        Assertions.assertThrows(StoreException.class,()->booksController.addBook(request));
    }

    @Test
    public void testSearchBooks() {
        BookSearchRequest request = TestDataUtil.getBookSearchRequest();
        BookSearchResponse expectedResponse = TestDataUtil.getBookSearchResponse();

        Mockito.when(booksService.searchBooks(request)).thenReturn(expectedResponse);

        BookSearchResponse result = booksController.searchBook(request);

        Assertions.assertEquals(expectedResponse.getResults().size(),result.getResults().size());
        Assertions.assertEquals(expectedResponse.getResults().get(0).getIsbn(),result.getResults().get(0).getIsbn());
    }
}
