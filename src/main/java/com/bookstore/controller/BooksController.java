package com.bookstore.controller;

import com.bookstore.request.AddBookRequest;
import com.bookstore.request.BookSearchRequest;
import com.bookstore.response.AddBookResponse;
import com.bookstore.response.BookSearchResponse;
import com.bookstore.service.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * Apis related to book entity.
 */
@RestController
@RequestMapping(value = "/api/v1/books")
public class BooksController {

    @Resource
    private BooksService booksService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddBookResponse addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        return booksService.addBook(addBookRequest);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public BookSearchResponse searchBook(BookSearchRequest bookSearchRequest){
        return booksService.searchBooks(bookSearchRequest);
    }

}
