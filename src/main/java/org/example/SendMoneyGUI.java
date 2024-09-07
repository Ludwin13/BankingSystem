package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMoneyGUI extends JFrame {
    private JTextField sendAmountField;
    private JTextField trackingNumberField;
    private JButton clearButton;
    private JButton sendButton;
    private JLabel balanceLabel;
    private JButton goBackButton;
    private JPanel sendMoneyPanel;
    private static Double userBalance;

    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    Connection conn = DBConnector.getConnection();

    public SendMoneyGUI(int user_id, Long current_tracking_number) {
        setContentPane(sendMoneyPanel);
        setTitle("Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        retrieveAccountBalance(user_id, current_tracking_number);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAmountField.setText("");
                trackingNumberField.setText("");
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String retrieve_receiver_account_details = "SELECT account_tracking_number," +
                            "account_balance " +
                            "FROM bank_account_information " +
                            "WHERE account_tracking_number = ?";

                    if (trackingNumberField.getText().equals(String.valueOf(current_tracking_number))) {
                        JOptionPane.showMessageDialog(null, "You cannot send money to yourself ");

                    } else {
                        try {

                            PreparedStatement ps = conn.prepareStatement(retrieve_receiver_account_details);
                            ps.setLong(1, Long.parseLong(trackingNumberField.getText()));
                            ResultSet rs = ps.executeQuery();

                            if(!rs.next()) {
                                JOptionPane.showMessageDialog(null, "Account does not exist");

                            } else {
                                if (hasSufficientBalance(current_tracking_number)) {
                                    do {
                                        Long account_tracking_number = rs.getLong("account_tracking_number");
                                        Double receiver_account_balance = rs.getDouble("account_balance");

                                        String update_receiver_balance = "UPDATE bank_account_information " +
                                                "SET account_balance = account_balance + ? " +
                                                "WHERE account_tracking_number = ?;";

                                        String update_sender_balance = "UPDATE bank_account_information " +
                                                "SET account_balance = account_balance - ? " +
                                                "WHERE account_tracking_number = ?;";

                                        String transaction_log = "INSERT INTO bank_account_transactions (" +
                                                "transaction_sender_account_num, " +
                                                "transaction_amount, " +
                                                "transaction_receiver_account_num, " +
                                                "transaction_date) " +
                                                "VALUES (?,?,?,?) ";


                                        if (Double.parseDouble(sendAmountField.getText()) <= 100 || sendAmountField.getText().isEmpty()) {
                                            JOptionPane.showMessageDialog(null, "You cannot send less than 100");

                                        } else {

                                            //Update Receiver Balance
                                            PreparedStatement ps_update = conn.prepareStatement(update_receiver_balance);
                                            ps_update.setDouble(1, Double.parseDouble(sendAmountField.getText()));
                                            ps_update.setLong(2, account_tracking_number);
                                            ps_update.executeUpdate();

                                            //Update Sender Balance
                                            PreparedStatement ps_update2 = conn.prepareStatement(update_sender_balance);
                                            ps_update2.setDouble(1, Double.parseDouble(sendAmountField.getText()));
                                            ps_update2.setLong(2, current_tracking_number);
                                            ps_update2.executeUpdate();

                                            //Create Transaction Log
                                            PreparedStatement create_transaction_log = conn.prepareStatement(transaction_log);
                                            create_transaction_log.setLong(1, current_tracking_number);
                                            create_transaction_log.setDouble(2, Double.parseDouble(sendAmountField.getText()));
                                            create_transaction_log.setLong(3, account_tracking_number);
                                            create_transaction_log.setString(4, ft.format(new Date()));
                                            create_transaction_log.executeUpdate();


                                            //Successful messages
                                            JOptionPane.showMessageDialog(null, "Successfully sent money");
                                            JOptionPane.showMessageDialog(null, "Your account balance is: " +
                                                    retrieveAccountBalance(user_id, current_tracking_number));
                                            JOptionPane.showMessageDialog(null, "Tracking Number: " + account_tracking_number +
                                                "\n Account Balance: " + receiver_account_balance +
                                                "\n New Receiver Balance: " + (receiver_account_balance + Double.parseDouble(sendAmountField.getText())));

                                            //Clear Fields
                                            trackingNumberField.setText("");
                                            sendAmountField.setText("");
                                        }

                                    } while(rs.next());

                                } else {
                                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                                }

                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }
                    }
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserHomeGUI userHomeGUI = new UserHomeGUI(user_id);
                setVisible(false);
                userHomeGUI.setVisible(true);
            }
        });
    }

    private Double retrieveAccountBalance(int user_id, Long current_tracking_number) {
        String retrieve_balance = "SELECT account_balance " +
                "FROM bank_account_information " +
                "WHERE account_tracking_number = ? AND " +
                "user_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(retrieve_balance);
            ps.setLong(1, current_tracking_number);
            ps.setInt(2, user_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                balanceLabel.setText(String.valueOf(rs.getDouble("account_balance")));
                userBalance = rs.getDouble("account_balance");
                return userBalance;
            }

        } catch (Exception ex) {

        }

        return null;
    }

    private boolean hasSufficientBalance(Long current_tracking_number) {
        String check_balance_query = "SELECT account_balance " +
                "FROM bank_account_information " +
                "WHERE account_tracking_number = ?";

        try {
            PreparedStatement check_balance_statement = conn.prepareStatement(check_balance_query);
            check_balance_statement.setLong(1, current_tracking_number);
            ResultSet rs = check_balance_statement.executeQuery();

            while(rs.next()) {
                Double account_balance = rs.getDouble("account_balance");
                if (account_balance > Double.parseDouble(sendAmountField.getText())) {
                    return true;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }


}
