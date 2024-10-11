package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.BorrowRecord;
import com.example.library.model.Database;
import com.example.library.view.BorrowReturnPanel;
import com.example.library.MainFrame;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.*;

public class BorrowController {
    private BorrowReturnPanel borrowPanel;
    private MainFrame mainFrame;

    public BorrowController(BorrowReturnPanel borrowPanel, MainFrame mainFrame) {
        this.borrowPanel = borrowPanel;
        this.mainFrame = mainFrame;
    }

    public void handleReturnBook(ActionEvent e) {
        int selectedRow = borrowPanel.getBorrowTable().getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(borrowPanel, "确定要归还选中的书籍吗？", "确认归还", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Database.returnBook(selectedRow);
                borrowPanel.refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(borrowPanel, "请选择一本书进行归还！");
        }
    }

    public void handleBorrowBook(ActionEvent e) {
        JTextField bookIdField = new JTextField();
        JTextField borrowerNameField = new JTextField();

        Object[] message = {
            "书籍ID:", bookIdField,
            "借书人:", borrowerNameField
        };

        int option = JOptionPane.showConfirmDialog(borrowPanel, message, "借出书籍", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int bookId = Integer.parseInt(bookIdField.getText().trim());
                String borrowerName = borrowerNameField.getText().trim();

                Book book = Database.getBooks().stream()
                        .filter(b -> b.getId() == bookId)
                        .findFirst()
                        .orElse(null);

                if (book == null) {
                    throw new IllegalArgumentException("书籍ID不存在！");
                }

                if (book.getStock() <= 0) {
                    throw new IllegalArgumentException("该书籍库存不足！");
                }

                if (borrowerName.isEmpty()) {
                    throw new IllegalArgumentException("借书人名称不能为空！");
                }

                // 减少库存
                book.setStock(book.getStock() - 1);

                // 添加借书记录
                BorrowRecord record = new BorrowRecord(book.getId(), book.getName(), borrowerName, LocalDate.now().toString());
                Database.getBorrowRecords().add(record);

                borrowPanel.refreshTable();

                JOptionPane.showMessageDialog(borrowPanel, "借书成功！");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(borrowPanel, "书籍ID必须是一个整数！");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(borrowPanel, ex.getMessage());
            }
        }
    }
}
