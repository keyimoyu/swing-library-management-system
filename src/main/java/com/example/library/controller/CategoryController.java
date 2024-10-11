package com.example.library.controller;

import com.example.library.model.Category;
import com.example.library.model.Database;
import com.example.library.view.CategoryManagementPanel;
import com.example.library.MainFrame;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.sql.SQLException;

public class CategoryController {
    private CategoryManagementPanel categoryPanel;
    private MainFrame mainFrame;

    public CategoryController(CategoryManagementPanel categoryPanel, MainFrame mainFrame) {
        this.categoryPanel = categoryPanel;
        this.mainFrame = mainFrame;
    }

    // 添加类别
    public void handleAddCategory(ActionEvent e) {
        String categoryName = JOptionPane.showInputDialog(categoryPanel, "请输入类别名称:", "添加类别", JOptionPane.PLAIN_MESSAGE);
        if (categoryName != null) {
            categoryName = categoryName.trim();
            if (!categoryName.isEmpty()) {
                try {
                    Database.addCategory(new Category(0, categoryName));  // id 将通过数据库生成
                    categoryPanel.refreshTable();  // 刷新表格数据
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(categoryPanel, "添加类别时发生错误！");
                }
            } else {
                JOptionPane.showMessageDialog(categoryPanel, "类别名称不能为空！");
            }
        }
    }

    // 删除类别
    public void handleDeleteCategory(ActionEvent e) {
        int selectedRow = categoryPanel.getCategoryTable().getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(categoryPanel, "确定要删除选中的类别吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // 获取表格中选中的类别 ID，假设类别 ID 是表格的第一列
                int categoryId = (int) categoryPanel.getCategoryTable().getValueAt(selectedRow, 0);

                try {
                    Database.deleteCategory(categoryId);
                    categoryPanel.refreshTable();  // 刷新表格数据
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(categoryPanel, "删除类别时发生错误！");
                }
            }
        } else {
            JOptionPane.showMessageDialog(categoryPanel, "请选择一个类别进行删除！");
        }
    }
}
