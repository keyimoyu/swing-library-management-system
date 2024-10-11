package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Database;
import com.example.library.view.BookManagementPanel;
import com.example.library.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class BookController {
    private BookManagementPanel bookPanel;
    private MainFrame mainFrame;

    public BookController(BookManagementPanel bookPanel, MainFrame mainFrame) {
        this.bookPanel = bookPanel;
        this.mainFrame = mainFrame;
    }

    public void handleAddBook(ActionEvent e) throws SQLException {
        JTextField nameField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField stockField = new JTextField();

        Object[] message = {
            "书名:", nameField,
            "作者:", authorField,
            "类别:", categoryField,
            "库存:", stockField
        };

        int option = JOptionPane.showConfirmDialog(bookPanel, message, "添加书籍", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String author = authorField.getText().trim();
            String category = categoryField.getText().trim();
            int stock;
            try {
                stock = Integer.parseInt(stockField.getText().trim());
                if (name.isEmpty() || author.isEmpty() || category.isEmpty()) {
                    throw new IllegalArgumentException("所有字段都必须填写！");
                }
                Book book = new Book(name, author, category, stock);
                Database.addBook(book);
                bookPanel.refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(bookPanel, "库存必须是一个整数！");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(bookPanel, ex.getMessage());
            }
        }
    }

    public void handleEditBook(ActionEvent e) {
        int selectedRow = bookPanel.getBookTable().getSelectedRow();
        if (selectedRow != -1) {
            Book book = Database.getBooks().get(selectedRow);
            JTextField nameField = new JTextField(book.getName());

            Object[] message = {
                "新的书名:", nameField
            };

            int option = JOptionPane.showConfirmDialog(bookPanel, message, "编辑书籍", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String newName = nameField.getText().trim();
                if (!newName.isEmpty()) {
                    Database.editBook(selectedRow, newName);
                    bookPanel.refreshTable();
                } else {
                    JOptionPane.showMessageDialog(bookPanel, "书名不能为空！");
                }
            }
        } else {
            JOptionPane.showMessageDialog(bookPanel, "请选择一个书籍进行编辑！");
        }
    }


    public void handleDeleteBook(ActionEvent e) {
        int selectedRow = bookPanel.getBookTable().getSelectedRow();
        if (selectedRow != -1) {
            // 获取表格中选中的书籍 ID，假设书籍 ID 是表格的第一列
            int bookId = (int) bookPanel.getBookTable().getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(bookPanel, "确定要删除选中的书籍吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // 调用 Database 类中的删除书籍方法
                    Database.deleteBook(bookId);
                    bookPanel.refreshTable();  // 刷新表格数据
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(bookPanel, "删除书籍时发生错误！");
                }
            }
        } else {
            JOptionPane.showMessageDialog(bookPanel, "请选择一个书籍进行删除！");
        }
    }
}
