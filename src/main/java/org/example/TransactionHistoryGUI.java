package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TransactionHistoryGUI extends JFrame {

    Connection conn = DBConnector.getConnection();
    private JPanel transactionHistoryPanel;
    private JTable transactionHistoryTable;
    private JButton goBackButton;

    public TransactionHistoryGUI(int user_id, long account_tracking_number) {
        setContentPane(transactionHistoryPanel);
        setTitle("Transaction History");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480,500);
        setLocationRelativeTo(null);
        setVisible(true);
        String transaction_history_query = "SELECT * " +
                "FROM bank_account_transactions " +
                "WHERE transaction_sender_account_num = ?;";

        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"transaction-id",
                "transaction_sender_account_num",
                "transaction_amount",
                "transaction_receiver_account_num",
                "transaction_date"};
        int getColumnCount = columnNames.length;
        tableModel.setColumnIdentifiers(columnNames);
        transactionHistoryTable.setModel(tableModel);


        try {
            PreparedStatement ps = conn.prepareStatement(transaction_history_query);
            ps.setLong(1, account_tracking_number);
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

//            String[] columnNames = new String[getColumnCount];
//            for (int i = 1; i <= getColumnCount; i++) {
//                columnNames[i - 1] = rsmd.getColumnName(i);
//            }


            while(rs.next()) {
                Object[] row = new Object[getColumnCount];
                for (int i = 1; i <= getColumnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);


//                System.out.println(rs.getString("transaction_id") + " " +
//                        rs.getLong("transaction_sender_account_num") + " " +
//                        rs.getDouble("transaction_amount") + " " +
//                        rs.getLong("transaction_receiver_account_num") + " " +
//                        rs.getString("transaction_date"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserHomeGUI user_home = new UserHomeGUI(user_id);
                setVisible(false);
                user_home.setVisible(true);
            }
        });
    }


}