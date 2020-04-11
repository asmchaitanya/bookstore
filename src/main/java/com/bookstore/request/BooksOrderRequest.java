package com.bookstore.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BooksOrderRequest {

    @NotNull(message = "BookId cannot be null")
    private Long bookId;

    @Min(value = 1, message = "Quantity should be at least 1")
    @Max(value = 5, message = "Order quantity cannot exceed 5")
    private Integer quantity;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
