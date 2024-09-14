package org.example;

import org.example.dao.BankAccountInformationDAO;
import org.example.model.BankAccountInformation;
import org.example.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SendMoneyGUI extends JFrame {
    private JTextField sendAmountField;
    private JTextField trackingNumberField;
    private JButton clearButton;
    private JButton sendButton;
    private JLabel currentBalanceLabel;
    private JButton goBackButton;
    private JPanel sendMoneyPanel;
    private JComboBox bankAccountComboBox;
    private static Double userBalance;

    Connection conn = DBConnector.getConnection();;
    BankAccountInformationDAO  bankAccountInformationDAO = new BankAccountInformationDAO();

    public SendMoneyGUI(User user, BankAccountInformation bankAccountInformation) {
        setContentPane(sendMoneyPanel);
        setTitle("Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        List<BankAccountInformation> bankAccountInformations = bankAccountInformationDAO.retrieveUserBalance(user);
        ArrayList<Long> bankAccounts = new ArrayList<>();
        //user_info_arr[0] = tracking_number

        //iterate through user's bank accounts by accessing the bankaccountinformation object
        for (BankAccountInformation accounts : bankAccountInformations) {
            String[] user_info_arr = String.valueOf(accounts).split(":");
            System.out.println(user_info_arr[0] + " " + user_info_arr[2]);
            bankAccounts.add(Long.parseLong(user_info_arr[0]));
        }

        //Add all user's bound bank accounts.
        bankAccountComboBox.setModel(new DefaultComboBoxModel<>(bankAccounts.toArray()));
        clearButton.addActionListener(e -> clearFields());

        sendButton.addActionListener(e -> {
            bankAccountInformationDAO.sendMoney(bankAccountInformation, Long.parseLong(trackingNumberField.getText()), Double.parseDouble(sendAmountField.getText()));
            currentBalanceLabel.setText(String.valueOf(bankAccountInformationDAO.retrieveBankAccountInformation(bankAccountInformation).getAccount_balance()));
            clearFields();
        });

        goBackButton.addActionListener(e -> {
            UserHomeGUI userHomeGUI = new UserHomeGUI(user);
            setVisible(false);
            userHomeGUI.setVisible(true);
        });

        bankAccountComboBox.addActionListener(e -> {
            BankAccountInformation bankAccountInformation1 = new BankAccountInformation(Long.parseLong(bankAccountComboBox.getSelectedItem().toString()), user.getUser_id());
            currentBalanceLabel.setText(String.valueOf(bankAccountInformationDAO.retrieveBankAccountInformation(bankAccountInformation1).getAccount_balance()));
        });
    }

    private void clearFields() {
        sendAmountField.setText("");
        trackingNumberField.setText("");
    }

//    private Double retrieveAccountBalance(int user_id, Long current_tracking_number) {
//        String retrieve_balance = "SELECT account_balance " +
//                "FROM bank_account_information " +
//                "WHERE account_tracking_number = ? AND " +
//                "user_id = ?";
//
//        try {
//            PreparedStatement ps = conn.prepareStatement(retrieve_balance);
//            ps.setLong(1, current_tracking_number);
//            ps.setInt(2, user_id);
//
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                currentBalanceLabel.setText(String.valueOf(rs.getDouble("account_balance")));
//                userBalance = rs.getDouble("account_balance");
//                return userBalance;
//            }
//
//        } catch (Exception ex) {
//
//        }
//
//        return null;
//    }


}
