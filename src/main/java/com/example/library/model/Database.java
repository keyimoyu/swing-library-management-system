package com.example.library.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<User> users = new ArrayList<>();
    private static List<Book> books = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();
    private static List<BorrowRecord> borrowRecords = new ArrayList<>();
    private static int bookIdCounter = 1;

    static {
        // 初始化一些数据
        users.add(new User("admin", "admin"));
        categories.add(new Category("编程"));
        books.add(new Book("Java 编程", "Author A", "编程", 10));
        books.get(0).setId(bookIdCounter++);
        borrowRecords.add(new BorrowRecord(1, "Java 编程", "admin", "2024-01-01"));
    }

    // 用户管理相关方法
    public static boolean login(String username, String password) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    public static boolean register(String username, String password) {
        if (users.stream().anyMatch(user -> user.getUsername().equals(username))) {
            return false;
        }
        users.add(new User(username, password));
        return true;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void deleteUser(int index) {
        users.remove(index);
    }

    // 类别管理相关方法
    public static List<Category> getCategories() {
        return categories;
    }

    public static void addCategory(Category category) {
        categories.add(category);
    }

    public static void deleteCategory(int index) {
        categories.remove(index);
    }

    // 图书管理相关方法
    public static List<Book> getBooks() {
        return books;
    }

    public static void addBook(Book book) {
        book.setId(bookIdCounter++);
        books.add(book);
    }

    public static void editBook(int index, String newBookName) {
        books.get(index).setName(newBookName);
    }

    public static void deleteBook(int index) {
        books.remove(index);
    }

    // 借还书籍相关方法
    public static List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public static void returnBook(int index) {
        borrowRecords.remove(index);
    }
}
