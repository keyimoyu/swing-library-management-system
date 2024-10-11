package com.example.library.controller;

import com.example.library.model.Category;
import com.example.library.model.Database;
import com.example.library.view.CategoryManagementPanel;
import com.example.library.MainFrame;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

public class CategoryController {
    private CategoryManagementPanel categoryPanel;
    private MainFrame mainFrame;

    public CategoryController(CategoryManagementPanel categoryPanel, MainFrame mainFrame) {
        this.categoryPanel = categoryPanel;
        this.mainFrame = mainFrame;
    }

    public void handleAddCategory(ActionEvent e) {
        String categoryName = JOptionPane.showInputDialog(categoryPanel, "请输入类别名称:", "添加类别", JOptionPane.PLAIN_MESSAGE);
        if (categoryName != null) {
            categoryName = categoryName.trim();
            if (!categoryName.isEmpty()) {
                Database.addCategory(new Category(categoryName));
                categoryPanel.refreshTable();
            } else {
                JOptionPane.showMessageDialog(categoryPanel, "类别名称不能为空！");
            }
        }
    }

    public void handleDeleteCategory(ActionEvent e) {
        int selectedRow = categoryPanel.getCategoryTable().getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(categoryPanel, "确定要删除选中的类别吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Database.deleteCategory(selectedRow);
                categoryPanel.refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(categoryPanel, "请选择一个类别进行删除！");
        }
    }
}
