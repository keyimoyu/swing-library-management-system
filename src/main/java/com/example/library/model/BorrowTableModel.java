package com.example.library.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BorrowTableModel extends AbstractTableModel {
    private String[] columnNames = {"书籍ID", "书名", "借书人", "借书日期"};
    private List<BorrowRecord> borrowRecords;

    public BorrowTableModel() {
        this.borrowRecords = Database.getBorrowRecords();
    }

    public void refreshData() {
        borrowRecords = Database.getBorrowRecords();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return borrowRecords.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BorrowRecord record = borrowRecords.get(rowIndex);
        switch (columnIndex) {
            case 0: return record.getBookId();
            case 1: return record.getBookName();
            case 2: return record.getBorrowerName();
            case 3: return record.getBorrowDate();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
