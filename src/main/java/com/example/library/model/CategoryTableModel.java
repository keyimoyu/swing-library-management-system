package com.example.library.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CategoryTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "类别名称"};
    private List<Category> categories;

    public CategoryTableModel() {
        this.categories = Database.getCategories();
    }

    public void refreshData() {
        categories = Database.getCategories();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Category category = categories.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return category.getName();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
