package com.bookstore.bookstore.service;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.entity.BookEntity;
import com.bookstore.es.ESIndexerService;
import com.bookstore.es.ESSearchService;
import com.bookstore.exception.StoreException;
import com.bookstore.repository.BooksRepository;
import com.bookstore.request.AddBookRequest;
import com.bookstore.request.BookSearchRequest;
import com.bookstore.response.AddBookResponse;
import com.bookstore.response.BookSearchResponse;
import com.bookstore.service.BooksService;
import com.bookstore.service.StockService;
import com.bookstore.service.impl.BooksServiceImpl;
import com.bookstore.vo.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Arrays;
import java.util.List;

import static com.bookstore.bookstore.util.TestDataUtil.AUTHOR;
import static com.bookstore.bookstore.util.TestDataUtil.BOOK_ID;
import static com.bookstore.bookstore.util.TestDataUtil.ISBN;
import static com.bookstore.bookstore.util.TestDataUtil.QUANTITY;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @Mock
    private StockService stockService;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private ESIndexerService esIndexerService;

    @Mock
    private ESSearchService esSearchService;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private BooksService booksService = new BooksServiceImpl();

    @Test
    public void testAddBook() {
        AddBookRequest request = TestDataUtil.getAddBookRequest();
        Book book = TestDataUtil.getBook();
        BookEntity beforeSaveEntity = getBookEntity();
        BookEntity savedEntity = getBookEntity();
        beforeSaveEntity.setId(null);
        AddBookResponse expectedResponse = TestDataUtil.getAddBookResponse();

        Mockito.when(conversionService.convert(Mockito.any(Object.class), Mockito.any()))
                .thenReturn(beforeSaveEntity, book, expectedResponse);
        Mockito.doNothing().when(esIndexerService).indexBooks(book);
        Mockito.when(booksRepository.save(beforeSaveEntity)).thenReturn(savedEntity);
        Mockito.doNothing().when(stockService).initializeStock(savedEntity.getId(), QUANTITY);

        AddBookResponse result = booksService.addBook(request);

        Assertions.assertEquals(expectedResponse.getIsbn(), result.getIsbn());
    }

    @Test
    public void testAddBookWithDuplicateRequest() {
        AddBookRequest request = TestDataUtil.getAddBookRequest();
        BookEntity bookEntity = getBookEntity();

        Mockito.when(booksRepository.findByIsbn(ISBN)).thenReturn(bookEntity);

        Assertions.assertThrows(StoreException.class, () -> booksService.addBook(request));
    }

    @Test
    public void testSearchBooks() {
        BookSearchRequest request = TestDataUtil.getBookSearchRequest();
        List<Book> books = Arrays.asList(TestDataUtil.getBook());

        Mockito.when(esSearchService.searchBooks(request)).thenReturn(books);

        BookSearchResponse response = booksService.searchBooks(request);

        Assertions.assertEquals(books, response.getResults());
    }

    @Test
    public void testSearchBooksForException() {
        BookSearchRequest request = TestDataUtil.getBookSearchRequest();
        List<Book> books = Arrays.asList(TestDataUtil.getBook());

        Mockito.when(esSearchService.searchBooks(request)).thenThrow(new StoreException("Error in searching books"));

        Assertions.assertThrows(StoreException.class, () -> booksService.searchBooks(request));
    }

    private BookEntity getBookEntity() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(TestDataUtil.TITLE);
        bookEntity.setIsbn(ISBN);
        bookEntity.setAuthor(AUTHOR);
        bookEntity.setId(BOOK_ID);

        return bookEntity;

    }


}
