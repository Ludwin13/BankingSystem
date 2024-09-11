package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DepositMoneyGUI extends JFrame {
    private JButton goBackButton;
    private JButton clearFieldsButton;
    private JButton depositMoneyButton;
    private JTextField depositAmountField;
    private JComboBox accountNumberComboBox;
    private JPanel depositMoneyPanel;

    Connection conn = DBConnector.getConnection();

    public DepositMoneyGUI(int id, long current_tracking_number) {
        setContentPane(depositMoneyPanel);
        setTitle("Deposit Money");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        fillInformation(id, current_tracking_number);

        goBackButton.addActionListener(e -> {
            UserHomeGUI userHomeGUI = new UserHomeGUI(id);
            setVisible(false);
            userHomeGUI.setVisible(true);
        });

        clearFieldsButton.addActionListener(e -> depositAmountField.setText(""));

        depositMoneyButton.addActionListener(e -> {
            String deposit_amount_query = "UPDATE bank_account_information " +
                    "SET account_balance = account_balance + ? " +
                    "WHERE account_tracking_number = ?;";

            if (Double.parseDouble(depositAmountField.getText()) <= 100 || depositAmountField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid deposit amount");
            } else {
                try {
                    PreparedStatement ps = conn.prepareStatement(deposit_amount_query);
                    ps.setFloat(1, Float.parseFloat(depositAmountField.getText()));
                    ps.setLong(2, Long.parseLong(accountNumberComboBox.getSelectedItem().toString()));
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Deposit Money Successfully");
                    depositAmountField.setText("");

                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                }
        });
    }

    private void fillInformation(int user_id, long current_tracking_number) {
        String find_bound_accounts = "SELECT account_tracking_number " +
                "FROM bank_account_information " +
                "WHERE user_id = ?;";

        try {
            PreparedStatement ps = conn.prepareStatement(find_bound_accounts);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();

            ArrayList<Long> account_numbers = new ArrayList<>();


            while(rs.next()) {
                Long bound_account_number = rs.getLong("account_tracking_number");
                account_numbers.add(bound_account_number);

            }

                accountNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(account_numbers.toArray()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }


    }
}
