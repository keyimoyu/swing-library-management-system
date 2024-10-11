package com.example.library.model;

import lombok.Data;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Database {
    // 提供给其他类使用的数据获取方法
    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<Book> books = new ArrayList<>();
    @Getter
    private static List<Category> categories = new ArrayList<>();
    @Getter
    private static List<BorrowRecord> borrowRecords = new ArrayList<>();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_data";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql";

    static {
        try {
            loadUsers();
            loadCategories();
            loadBooks();
            loadBorrowRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 加载类别数据
    private static void loadCategories() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String query = "SELECT id, name FROM categories";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            categories.add(new Category(id, name));  // 修改为 int 类型的 id
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    // 添加类别
    public static void addCategory(Category category) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String insertSQL = "INSERT INTO categories (name) VALUES (?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, category.getName());
        pstmt.executeUpdate();

        // 获取生成的类别 ID
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int generatedId = rs.getInt(1);
            category.setId(generatedId);  // 设置生成的 id
            categories.add(category);  // 添加到内存中
        }

        rs.close();
        pstmt.close();
        conn.close();
    }

    // 删除类别
    public static void deleteCategory(int categoryId) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String deleteSQL = "DELETE FROM categories WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
        pstmt.setInt(1, categoryId);
        pstmt.executeUpdate();

        // 从内存中删除该类别
        categories.removeIf(category -> category.getId() == categoryId);

        pstmt.close();
        conn.close();
    }


    // 加载用户数据
    private static void loadUsers() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String query = "SELECT username, password FROM users";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            users.add(new User(username, password));
        }

        rs.close();
        stmt.close();
        conn.close();
    }



    // 加载书籍数据
    private static void loadBooks() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String query = "SELECT id, name, author, category_id, stock FROM books";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String author = rs.getString("author");
            int categoryId = rs.getInt("category_id");
            int stock = rs.getInt("stock");

            // 找到相应类别的名称
            String categoryName = categories.stream()
                    .filter(category -> category.getId() == categoryId)
                    .findFirst()
                    .map(Category::getName)
                    .orElse("未知类别");

            Book book = new Book(name, author, categoryName, stock);
            book.setId(id);
            books.add(book);
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    // 加载借书记录数据
    private static void loadBorrowRecords() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String query = "SELECT book_id, book_name, borrower_name, borrow_date FROM borrow_records";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int bookId = rs.getInt("book_id");
            String bookName = rs.getString("book_name");
            String borrowerName = rs.getString("borrower_name");
            String borrowDate = rs.getString("borrow_date");

            borrowRecords.add(new BorrowRecord(bookId, bookName, borrowerName, borrowDate));
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    // TODO
    public static void deleteUser(int selectedRow) {}
        // 图书管理相关方法
    public static List<Book> getBooks() {
        return books;
    }

    public static void addBook(Book book) {
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

    public static boolean register(String username, String password) {
        return true;
    }

    public static boolean login(String username, String password) {
        return true;
    }
}

