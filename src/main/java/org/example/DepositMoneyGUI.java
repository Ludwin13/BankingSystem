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

public class DepositMoneyGUI extends JFrame {
    private JButton goBackButton;
    private JButton clearFieldsButton;
    private JButton depositMoneyButton;
    private JTextField depositAmountField;
    private JComboBox bankAccountComboBox;
    private JPanel depositMoneyPanel;
    private JLabel accountBalanceLabel;

    Connection conn = DBConnector.getConnection();
    BankAccountInformationDAO bankAccountInformationDAO = new BankAccountInformationDAO();

    public DepositMoneyGUI(User user, BankAccountInformation bankAccountInformation) {
        setContentPane(depositMoneyPanel);
        setTitle("Deposit Money");
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
        bankAccountComboBox.setModel(new DefaultComboBoxModel<>(bankAccounts.toArray()));

//        fillInformation(user.getUser_id(), bankAccountInformation.getAccount_tracking_number());

        goBackButton.addActionListener(e -> {
            UserHomeGUI userHomeGUI = new UserHomeGUI(user);
            setVisible(false);
            userHomeGUI.setVisible(true);
        });

        clearFieldsButton.addActionListener(e -> depositAmountField.setText(""));

        depositMoneyButton.addActionListener(e -> {
            boolean status = bankAccountInformationDAO.depositMoney(bankAccountInformation, Long.parseLong(bankAccountComboBox.getSelectedItem().toString()),
                    Double.parseDouble(depositAmountField.getText()));
            if (!status) {
                JOptionPane.showMessageDialog(null, "Error");
            } else {
                JOptionPane.showMessageDialog(null, "Successfully deposited money.");
                depositAmountField.setText("");
                accountBalanceLabel.setText(String.valueOf(bankAccountInformationDAO.retrieveBankAccountInformation(bankAccountInformation).getAccount_balance()));
            }

        });
        bankAccountComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankAccountInformation bankAccountInformationComboBox = new BankAccountInformation(Long.parseLong(bankAccountComboBox.getSelectedItem().toString()), user.getUser_id());
                accountBalanceLabel.setText(String.valueOf(bankAccountInformationDAO.retrieveBankAccountInformation(bankAccountInformationComboBox).getAccount_balance()));
            }
        });
    }

//    private void fillInformation(int user_id, long current_tracking_number) {
//        String find_bound_accounts = "SELECT account_tracking_number " +
//                "FROM bank_account_information " +
//                "WHERE user_id = ?;";
//
//        try {
//            PreparedStatement ps = conn.prepareStatement(find_bound_accounts);
//            ps.setInt(1, user_id);
//            ResultSet rs = ps.executeQuery();
//
//            ArrayList<Long> account_numbers = new ArrayList<>();
//
//            while(rs.next()) {
//                Long bound_account_number = rs.getLong("account_tracking_number");
//                account_numbers.add(bound_account_number);
//            }
//            bankAccountComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(account_numbers.toArray()));
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//        }
//    }
}
