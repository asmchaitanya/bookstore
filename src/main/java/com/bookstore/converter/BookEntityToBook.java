package com.bookstore.converter;

import com.bookstore.entity.BookEntity;
import com.bookstore.vo.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookEntityToBook implements Converter<BookEntity, Book> {
    @Override
    public Book convert(BookEntity bookEntity) {
        Book book = new Book();
        book.setAuthor(bookEntity.getAuthor());
        book.setDescription(bookEntity.getDescription());
        book.setIsbn(bookEntity.getIsbn());
        book.setTitle(bookEntity.getTitle());
        book.setBookId(bookEntity.getId());

        return book;
    }
}
