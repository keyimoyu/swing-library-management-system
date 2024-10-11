package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.LoginController;

import javax.swing.*;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LoginController controller;

    public LoginPanel(MainFrame mainFrame) {
        controller = new LoginController(mainFrame, this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(controller::handleLogin);

        JButton registerButton = new JButton("注册");
        registerButton.addActionListener(e -> mainFrame.showPanel("Register"));

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
