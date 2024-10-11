package com.example.library;

import com.example.library.view.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("图书管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new CardLayout());
        setContentPane(mainPanel);

        // 添加模块页面
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new RegisterPanel(this), "Register");
        mainPanel.add(new BookManagementPanel(this), "BookManagement");
        mainPanel.add(new UserManagementPanel(this), "UserManagement");
        mainPanel.add(new CategoryManagementPanel(this), "CategoryManagement");
        mainPanel.add(new BorrowReturnPanel(this), "BorrowReturn");

        showPanel("Login");
    }

    public void showPanel(String panelName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
