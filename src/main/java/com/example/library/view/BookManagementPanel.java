package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.BookController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class BookManagementPanel extends JPanel {
    private JTable bookTable;
    private BookTableModel bookTableModel;
    private BookController controller;

    public BookManagementPanel(MainFrame mainFrame) {
        controller = new BookController(this, mainFrame);

        setLayout(new BorderLayout());

        JButton addBookButton = new JButton("添加书籍");
        JButton editBookButton = new JButton("编辑书籍");
        JButton deleteBookButton = new JButton("删除书籍");
        JButton manageBorrowButton = new JButton("借还管理");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBookButton);
        buttonPanel.add(editBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(manageBorrowButton);

        // 使用自定义的BookTableModel来管理表格数据
        bookTableModel = new BookTableModel();
        bookTable = new JTable(bookTableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        addBookButton.addActionListener(e2 -> {
            try {
                controller.handleAddBook(e2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        editBookButton.addActionListener(controller::handleEditBook);
        deleteBookButton.addActionListener(e1 -> {
            controller.handleDeleteBook(e1);
        });
        manageBorrowButton.addActionListener(e -> {
            mainFrame.showPanel("BorrowReturn");
        });
    }

    public JTable getBookTable() {
        return bookTable;
    }

    public void refreshTable() throws SQLException {
        bookTableModel.refreshData();
    }
}
