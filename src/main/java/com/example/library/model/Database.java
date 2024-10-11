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


    public static void editBook(int index, String newBookName) {
        books.get(index)
                .setName(newBookName);
    }

    // 删除书籍的方法
    public static void deleteBook(int bookId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 首先删除与该书籍相关的借书记录
            String deleteBorrowRecordsSQL = "DELETE FROM borrow_records WHERE book_id = ?";
            pstmt = conn.prepareStatement(deleteBorrowRecordsSQL);
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
            pstmt.close();

            // 然后删除书籍记录
            String deleteBookSQL = "DELETE FROM books WHERE id = ?";
            pstmt = conn.prepareStatement(deleteBookSQL);
            pstmt.setInt(1, bookId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("书籍删除成功。");
            } else {
                System.out.println("书籍删除失败，可能该书籍不存在。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }


    // 借还书籍相关方法
    public static List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public static void returnBook(int index) {
        borrowRecords.remove(index);
    }

    public static boolean register(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 检查用户名是否已经存在
            String checkUserSQL = "SELECT * FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(checkUserSQL);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 用户名已存在
                return false;
            }

            // 如果用户名不存在，插入新用户
            String insertUserSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertUserSQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0; // 如果插入成功，返回 true

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 用户登录方法
    public static boolean login(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 查询用户名和密码是否匹配
            String loginSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(loginSQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            return rs.next();  // 如果存在匹配的记录，返回 true，否则返回 false

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // 添加书籍的方法
    public static void addBook(Book book) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 根据类别名称获取类别ID
            int categoryId = getCategoryIDByName(book.getCategory());
            if (categoryId == -1) {
                throw new SQLException("类别不存在，请先添加类别。");
            }

            // 插入书籍的 SQL 语句
            String insertSQL = "INSERT INTO books (name, author, category_id, stock) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, categoryId);  // 设置正确的 category_id
            pstmt.setInt(4, book.getStock());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // 获取生成的书籍 ID
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    book.setId(generatedId);  // 设置生成的书籍 ID
                    books.add(book);  // 添加到内存中的 books 列表
                }
                rs.close();
            } else {
                System.out.println("添加书籍时没有成功插入到数据库。");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    // 根据类别名称获取类别ID
    private static int getCategoryIDByName(String categoryName) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int categoryId = -1;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT id FROM categories WHERE name = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, categoryName);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                categoryId = rs.getInt("id");
            } else {
                System.out.println("类别 '" + categoryName + "' 不存在。");
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return categoryId;
    }

    public static void loadDataFromDatabase() throws SQLException {
        // 清空现有的数据，避免重复加载
        users.clear();
        categories.clear();
        books.clear();
        borrowRecords.clear();

        // 加载各个数据表的数据
        loadUsers();
        loadCategories();
        loadBooks();
        loadBorrowRecords();
    }

}



