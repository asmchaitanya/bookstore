package com.bookstore.converter;

import com.bookstore.entity.BookEntity;
import com.bookstore.response.AddBookResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookEntityToAddBookResponse implements Converter<BookEntity, AddBookResponse> {

    @Override
    public AddBookResponse convert(BookEntity source) {

        AddBookResponse result = new AddBookResponse();
        result.setAuthor(source.getAuthor());
        result.setDescription(source.getDescription());
        result.setIsbn(source.getIsbn());
        result.setTitle(source.getTitle());

        return result;
    }
}
