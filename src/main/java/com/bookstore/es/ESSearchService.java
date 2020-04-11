package com.bookstore.es;

import com.bookstore.request.BookSearchRequest;
import com.bookstore.request.PostsSearchRequest;
import com.bookstore.vo.Book;
import com.bookstore.vo.Post;

import java.io.IOException;
import java.util.List;

public interface ESSearchService {

    List<Book> searchBooks(BookSearchRequest bookSearchRequest);

    List<Post> searchPosts(PostsSearchRequest postsSearchRequest);
}
