package com.bookstore.es;

import com.bookstore.vo.Book;
import com.bookstore.vo.Post;

import java.util.List;

public interface ESIndexerService {

    void indexBooks(Book book);

    void indexPosts(List<Post> posts);
}
