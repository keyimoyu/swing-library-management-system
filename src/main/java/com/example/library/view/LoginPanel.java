package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LoginController controller;

    public LoginPanel(MainFrame mainFrame) {
        controller = new LoginController(mainFrame, this);

        // 使用 GridBagLayout 布局管理器
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 给每个组件周围添加一些空白
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 用户名标签和文本框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置标签字体
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置文本框字体

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc); // 添加用户名标签

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc); // 添加用户名输入框

        // 密码标签和密码框
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc); // 添加密码标签

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc); // 添加密码输入框

        // 登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 16)); // 设置按钮字体
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setOpaque(true); // 设置背景可见
        loginButton.addActionListener(controller::handleLogin); // 绑定登录事件

        // 注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setOpaque(true);
        registerButton.addActionListener(e -> mainFrame.showPanel("Register")); // 绑定注册事件

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 按钮跨两列显示
        add(buttonPanel, gbc); // 添加按钮面板
    }

    // 获取用户名
    public String getUsername() {
        return usernameField.getText();
    }

    // 获取密码
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // 显示提示消息
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}


