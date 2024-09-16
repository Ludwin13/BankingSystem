package org.example;

import org.example.model.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankTransactionsGUI extends JFrame{
    private JPanel BankTransactionsPanel;
    private JTable transactionsTable;
    private JButton goBackButton;

    public BankTransactionsGUI(Admin admin){
        setContentPane(BankTransactionsPanel);
        setTitle("Transaction History");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480,500);
        setLocationRelativeTo(null);
        setVisible(true);

        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"transaction-id",
                "transaction_type",
                "transaction_sender_account_num",
                "transaction_amount",
                "transaction_receiver_account_num",
                "transaction_date"};

        int getColumnCount = columnNames.length;

        tableModel.setColumnIdentifiers(columnNames);
        transactionsTable.setModel(tableModel);

        String transaction_history_query = "SELECT * " +
                "FROM bank_account_transactions";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(transaction_history_query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Object[] row = new Object[getColumnCount];
                for (int i = 1; i <= getColumnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminGUI adminGUI = new AdminGUI(admin);
                setVisible(false);
                adminGUI.setVisible(true);
            }
        });

    }


}
