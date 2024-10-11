package com.example.library.view;

import com.example.library.MainFrame;
import com.example.library.controller.CategoryController;
import com.example.library.model.CategoryTableModel;

import javax.swing.*;
import java.awt.*;

public class CategoryManagementPanel extends JPanel {
    private JTable categoryTable;
    private CategoryTableModel categoryTableModel;
    private CategoryController controller;

    public CategoryManagementPanel(MainFrame mainFrame) {
        controller = new CategoryController(this, mainFrame);

        setLayout(new BorderLayout());

        // 按钮区
        JButton addCategoryButton = new JButton("添加类别");
        JButton deleteCategoryButton = new JButton("删除类别");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addCategoryButton);
        buttonPanel.add(deleteCategoryButton);

        // 表格
        categoryTableModel = new CategoryTableModel();
        categoryTable = new JTable(categoryTableModel);
        JScrollPane scrollPane = new JScrollPane(categoryTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // 添加类别事件
        addCategoryButton.addActionListener(controller::handleAddCategory);
        // 删除类别事件
        deleteCategoryButton.addActionListener(controller::handleDeleteCategory);
    }

    public JTable getCategoryTable() {
        return categoryTable;
    }

    public void refreshTable() {
        categoryTableModel.refreshData();
    }
}
