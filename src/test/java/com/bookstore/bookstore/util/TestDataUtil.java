package com.bookstore.bookstore.util;

import com.bookstore.enums.OrderStatus;
import com.bookstore.request.AddBookRequest;
import com.bookstore.request.BookSearchRequest;
import com.bookstore.request.BooksOrderRequest;
import com.bookstore.response.AddBookResponse;
import com.bookstore.response.BookSearchResponse;
import com.bookstore.response.BooksOrderResponse;
import com.bookstore.vo.Book;
import com.bookstore.vo.Post;

import java.util.Arrays;
import java.util.Date;

public class TestDataUtil {

    public static Long BOOK_ID = 1001L;

    public static String AUTHOR = "scholar1";

    public static String ISBN = "test-1234abcd";

    public static String TITLE = "computer science";

    public static String DESCRIPTION = "computer science book.";

    public static Integer QUANTITY = 10;

    public static String EMAIL = "test@mail.com";

    public static Long ORDER_ID = 2002L;


    public static AddBookRequest getAddBookRequest() {
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setAuthor(AUTHOR);
        bookRequest.setIsbn(ISBN);
        bookRequest.setTitle(TITLE);
        bookRequest.setDescription(DESCRIPTION);

        return bookRequest;
    }

    public static AddBookResponse getAddBookResponse() {
        AddBookResponse response = new AddBookResponse();
        response.setAuthor(AUTHOR);
        response.setIsbn(ISBN);
        response.setTitle(TITLE);
        response.setDescription(DESCRIPTION);

        return response;
    }

    public static BookSearchRequest getBookSearchRequest() {
        BookSearchRequest request = new BookSearchRequest();
        request.setAuthor(AUTHOR);
        request.setTitle(TITLE);

        return request;
    }

    public static BookSearchResponse getBookSearchResponse() {
        BookSearchResponse response = new BookSearchResponse();
        response.setResults(Arrays.asList(getBook()));

        return response;
    }

    public static Book getBook() {
        Book book = new Book();
        book.setTitle(TITLE);
        book.setIsbn(ISBN);
        book.setDescription(DESCRIPTION);
        book.setAuthor(AUTHOR);

        return book;
    }

    public static BooksOrderRequest getBooksOrderRequest() {
        BooksOrderRequest request = new BooksOrderRequest();

        request.setQuantity(QUANTITY);
        request.setBookId(BOOK_ID);
        request.setEmail(EMAIL);

        return request;
    }

    public static BooksOrderResponse getBooksOrderResponse() {
        BooksOrderResponse response = new BooksOrderResponse();

        response.setOrderId(ORDER_ID);
        response.setStatus(OrderStatus.CONFIRMED);
        response.setCreatedDate(new Date(System.currentTimeMillis()));

        return response;
    }

    public static Post getPost() {
        Post post = new Post();

        post.setBody("post body");
        post.setTitle("post title");
        post.setUserId("post-user");
        post.setId("post-id");

        return post;
    }

}
