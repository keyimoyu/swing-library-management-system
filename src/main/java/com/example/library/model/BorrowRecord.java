package com.example.library.model;

import lombok.Data;

@Data
public class BorrowRecord {
    private int bookId;
    private String bookName;
    private String borrowerName;
    private String borrowDate;

    public BorrowRecord(int bookId, String bookName, String borrowerName, String borrowDate) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
    }

}
