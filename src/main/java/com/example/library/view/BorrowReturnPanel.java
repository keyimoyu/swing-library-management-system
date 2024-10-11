package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.BorrowController;
import com.example.library.model.BorrowTableModel;

import javax.swing.*;
import java.awt.*;

public class BorrowReturnPanel extends JPanel {
    private JTable borrowTable;
    private BorrowTableModel borrowTableModel;
    private BorrowController controller;

    public BorrowReturnPanel(MainFrame mainFrame) {
        controller = new BorrowController(this, mainFrame);

        setLayout(new BorderLayout());

        // 按钮区
        JButton returnBookButton = new JButton("归还书籍");
        JButton borrowBookButton = new JButton("借出书籍");
        JButton backButton = new JButton("返回主页面");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(borrowBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(backButton);
        // 表格
        borrowTableModel = new BorrowTableModel();
        borrowTable = new JTable(borrowTableModel);
        JScrollPane scrollPane = new JScrollPane(borrowTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        returnBookButton.addActionListener(controller::handleReturnBook);
        borrowBookButton.addActionListener(controller::handleBorrowBook);
        backButton.addActionListener(e -> {
            mainFrame.showPanel("BookManagement");  // 切换到主页面
        });
    }

    public JTable getBorrowTable() {
        return borrowTable;
    }

    public void refreshTable() {
        borrowTableModel.refreshData();
    }
}
