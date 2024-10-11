package com.example.library.view;

import com.example.library.model.Book;
import com.example.library.model.Database;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "书名", "作者", "类别", "库存"};
    private List<Book> books;

    public BookTableModel() {
        this.books = Database.getBooks();
    }

    public void refreshData() {
        books = Database.getBooks();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = books.get(rowIndex);
        switch (columnIndex) {
            case 0: return book.getId();
            case 1: return book.getName();
            case 2: return book.getAuthor();
            case 3: return book.getCategory();
            case 4: return book.getStock();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
