package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.UserController;
import com.example.library.model.UserTableModel;

import javax.swing.*;
import java.awt.*;

public class UserManagementPanel extends JPanel {
    private JTable userTable;
    private UserTableModel userTableModel;
    private UserController controller;

    public UserManagementPanel(MainFrame mainFrame) {
        controller = new UserController(this, UserController.Action.DELETE);

        setLayout(new BorderLayout());

        // 按钮区
        JButton deleteUserButton = new JButton("删除用户");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteUserButton);

        // 表格
        userTableModel = new UserTableModel();
        userTable = new JTable(userTableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // 删除用户按钮事件
        deleteUserButton.addActionListener(controller::handleDeleteUser);
    }

    public JTable getUserTable() {
        return userTable;
    }

    public void refreshTable() {
        userTableModel.refreshData();
    }
}
