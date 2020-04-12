package com.bookstore.request;

import javax.validation.constraints.NotBlank;

public class AddBookRequest {

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    @NotBlank(message = "Author name cannot bee blank")
    private String author;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
