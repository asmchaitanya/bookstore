package com.bookstore.service;

import com.bookstore.request.AddBookRequest;
import com.bookstore.request.BookSearchRequest;
import com.bookstore.response.AddBookResponse;
import com.bookstore.response.BookSearchResponse;
import com.bookstore.response.MediaCoverageResponse;
import com.bookstore.vo.Book;

public interface BooksService {

    /**
     * Add new book to the stock base.
     * @param addBookRequest
     * @return
     */

    AddBookResponse addBook(AddBookRequest addBookRequest);

    /**
     * Search books in the store with title, author, isbn
     * @param searchRequest
     * @return
     */
    BookSearchResponse searchBooks(BookSearchRequest searchRequest);

    Book findBookByIsbn(String isbn);

}
