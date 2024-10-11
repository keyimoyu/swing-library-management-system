package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.UserController;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserController controller;

    public RegisterPanel(MainFrame mainFrame) {
        controller = new UserController(this, mainFrame, UserController.Action.REGISTER);

        // 使用 GridBagLayout 布局管理器
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 给每个组件四周添加10px的间距
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 用户名标签和输入框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置输入框字体

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc); // 添加用户名标签

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc); // 添加用户名输入框

        // 密码标签和密码框
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置输入框字体

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc); // 添加密码标签

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc); // 添加密码输入框

        // 注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 16)); // 设置按钮字体
        registerButton.setBackground(Color.LIGHT_GRAY); // 设置按钮背景颜色
        registerButton.setOpaque(true); // 确保按钮背景颜色可见
        registerButton.addActionListener(controller::handleUserAction); // 绑定注册事件

        // 添加按钮到面板
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 按钮跨两列
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        add(buttonPanel, gbc);
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
