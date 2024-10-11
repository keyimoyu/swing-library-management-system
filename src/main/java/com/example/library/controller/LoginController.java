package com.example.library.controller;

import com.example.library.model.Database;
import com.example.library.view.LoginPanel;
import com.example.library.MainFrame;

import java.awt.event.ActionEvent;

public class LoginController {
    private MainFrame mainFrame;
    private LoginPanel loginPanel;

    public LoginController(MainFrame mainFrame, LoginPanel loginPanel) {
        this.mainFrame = mainFrame;
        this.loginPanel = loginPanel;
    }

    public void handleLogin(ActionEvent e) {
        String username = loginPanel.getUsername();
        String password = loginPanel.getPassword();

        if (Database.login(username, password)) {
            mainFrame.showPanel("BookManagement");
        } else {
            loginPanel.showMessage("登录失败，请检查用户名或密码。");
        }
    }
}
