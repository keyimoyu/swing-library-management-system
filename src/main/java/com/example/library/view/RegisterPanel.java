package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.UserController;

import javax.swing.*;

public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserController controller;

    public RegisterPanel(MainFrame mainFrame) {
        controller = new UserController(this, mainFrame, UserController.Action.REGISTER);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);

        JButton registerButton = new JButton("注册");
        registerButton.addActionListener(controller::handleUserAction);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
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
