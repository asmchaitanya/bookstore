package com.bookstore.response;

import com.bookstore.vo.Book;

import java.util.List;

public class BookSearchResponse {

    private List<Book> results;

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }
}
