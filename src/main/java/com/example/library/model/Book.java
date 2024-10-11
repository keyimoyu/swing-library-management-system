package com.example.library.model;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String name;
    private String author;
    private String category;
    private int stock;

    public Book(String name, String author, String category, int stock) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }

}
