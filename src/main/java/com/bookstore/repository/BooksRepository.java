package com.bookstore.repository;

import com.bookstore.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BookEntity, String> {

    BookEntity findByIsbn(String isbn);
}
