package com.bookstore.converter;

import com.bookstore.entity.BookEntity;
import com.bookstore.request.AddBookRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddRequestToBookEntity implements Converter<AddBookRequest, BookEntity> {

    @Override
    public BookEntity convert(AddBookRequest addBookRequest) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setAuthor(addBookRequest.getAuthor().toLowerCase());
        bookEntity.setDescription(addBookRequest.getDescription());
        bookEntity.setIsbn(addBookRequest.getIsbn());
        bookEntity.setTitle(addBookRequest.getTitle().toLowerCase());

        return bookEntity;
    }
}
