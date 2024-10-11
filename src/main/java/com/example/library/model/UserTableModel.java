package com.example.library.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "用户名"};
    private List<User> users;

    public UserTableModel() {
        this.users = Database.getUsers();
    }

    public void refreshData() {
        users = Database.getUsers();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return user.getUsername();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
