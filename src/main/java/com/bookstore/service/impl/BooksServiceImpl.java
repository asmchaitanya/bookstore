package com.bookstore.service.impl;

import com.bookstore.constants.ApplicationContants;
import com.bookstore.controller.BooksController;
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
import com.bookstore.service.PriceService;
import com.bookstore.service.StockService;
import com.bookstore.vo.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class BooksServiceImpl implements BooksService {

    private static Logger LOGGER = LoggerFactory.getLogger(BooksController.class);

    @Resource
    private BooksRepository booksRepository;

    @Resource
    private ConversionService conversionService;

    @Resource
    private ESIndexerService esIndexerService;

    @Resource
    private ESSearchService esSearchService;

    @Resource
    private StockService stockService;

    @Resource
    private PriceService priceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddBookResponse addBook(AddBookRequest addBookRequest) {

        BookEntity existingBook = booksRepository.findByIsbn(addBookRequest.getIsbn());
        if (null != existingBook) {
            throw new StoreException("Book with given isbn already exist.", HttpStatus.CONFLICT);
        }

        BookEntity bookEntity = conversionService.convert(addBookRequest, BookEntity.class);
        bookEntity.setCreatedAt(new Date(System.currentTimeMillis()));
        bookEntity.setUpdatedAt(new Date(System.currentTimeMillis()));

        BookEntity result = booksRepository.save(bookEntity);

        LOGGER.info("Book with isbn {} added successfully - {}.", addBookRequest.getIsbn(), bookEntity.getId());
        esIndexerService.indexBooks(conversionService.convert(result, Book.class));
        initializeStock(result.getId());
        setPrice(result.getId(), addBookRequest.getPrice());

        AddBookResponse response = conversionService.convert(result, AddBookResponse.class);
        response.setPrice(addBookRequest.getPrice());

        return response;
    }

    @Override
    public BookSearchResponse searchBooks(BookSearchRequest searchRequest) {
        List<Book> books = esSearchService.searchBooks(searchRequest);

        BookSearchResponse result = new BookSearchResponse();
        result.setResults(books);

        return result;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        BookEntity bookEntity = booksRepository.findByIsbn(isbn);
        return conversionService.convert(bookEntity, Book.class);
    }

    private void initializeStock(Long productId) {
        stockService.initializeStock(productId, ApplicationContants.REFILL_QUANTITY);
    }

    private void setPrice(Long productId, Double price) {
        priceService.setPrice(productId, price);
    }

}
