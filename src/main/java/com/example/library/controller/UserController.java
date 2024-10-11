package com.example.library.controller;

import com.example.library.model.Database;
import com.example.library.MainFrame;
import com.example.library.view.UserManagementPanel;
import com.example.library.view.RegisterPanel;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

public class UserController {
    public enum Action {
        REGISTER,
        DELETE
    }

    private RegisterPanel registerPanel;
    private UserManagementPanel userPanel;
    private MainFrame mainFrame;
    private Action action;

    // 构造方法用于注册
    public UserController(RegisterPanel registerPanel, MainFrame mainFrame, Action action) {
        this.registerPanel = registerPanel;
        this.mainFrame = mainFrame;
        this.action = action;
    }

    // 构造方法用于用户管理中的删除
    public UserController(UserManagementPanel userPanel, Action action) {
        this.userPanel = userPanel;
        this.action = action;
    }

    public void handleUserAction(ActionEvent e) {
        if (action == Action.REGISTER) {
            String username = registerPanel.getUsername();
            String password = registerPanel.getPassword();

            if (Database.register(username, password)) {
                registerPanel.showMessage("注册成功，请登录！");
                mainFrame.showPanel("Login");
            } else {
                registerPanel.showMessage("用户名已存在！");
            }
        }
    }

    public void handleDeleteUser(ActionEvent e) {
        int selectedRow = userPanel.getUserTable().getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(userPanel, "确定要删除选中的用户吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Database.deleteUser(selectedRow);
                userPanel.refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(userPanel, "请选择一个用户进行删除！");
        }
    }
}
