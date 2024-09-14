package org.example;

import org.example.dao.BankAccountInformationDAO;
import org.example.model.BankAccountInformation;
import org.example.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserHomeGUI extends JFrame {
    private JPanel UserHomePanel;
    private JPanel userInformationPanel;
    private JPanel userInformationValuesPanel;
    private JButton sendMoneyButton;
    private JButton transactionHistoryButton;
    private JLabel userNameLabel;
    private JLabel emailLabel;
    private JButton changePasswordButton;
    private JLabel fullNameLabel;
    private JLabel currentBalanceLabel;
    private JButton bindAccountButton;
    private JButton logoutButton;
    private JButton depositButton;
    private JComboBox bankAccountComboBox;


    BankAccountInformation bankAccountInformation = new BankAccountInformation();
    BankAccountInformationDAO bankAccountInformationDAO = new BankAccountInformationDAO();

    public UserHomeGUI(User user) {
        setContentPane(UserHomePanel);
        setTitle("User Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        fullNameLabel.setText(user.return_full_name());
        userNameLabel.setText(user.getUser_name());
        emailLabel.setText(user.getUser_email());

        //Set user balance
        List<BankAccountInformation> bankAccountInformations = bankAccountInformationDAO.retrieveUserBalance(user);
        ArrayList<Long> bankAccounts = new ArrayList<>();
        //user_info_arr[0] = tracking_number

        for (BankAccountInformation bankAccountInformation : bankAccountInformations) {
            String[] user_info_arr = String.valueOf(bankAccountInformation).split(":");
            System.out.println(user_info_arr[0] + " " + user_info_arr[2]);
            bankAccounts.add(Long.parseLong(user_info_arr[0]));
        }

        bankAccountComboBox.setModel(new DefaultComboBoxModel<>(bankAccounts.toArray()));

        BankAccountInformation bankAccountInformation = new BankAccountInformation(Long.parseLong(bankAccountComboBox.getSelectedItem().toString()), user.getUser_id());

        sendMoneyButton.addActionListener(e -> {
            if(bankAccountComboBox.getSelectedItem() != null) {
                SendMoneyGUI sendMoneyGUI = new SendMoneyGUI(user, bankAccountInformationDAO.retrieveBankAccountInformation(bankAccountInformation));
                setVisible(false);
                sendMoneyGUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "You have no existing bank account");
            }
        });

        depositButton.addActionListener(e -> {
            if(bankAccountComboBox.getSelectedItem() != null) {
                DepositMoneyGUI depositMoneyGUI = new DepositMoneyGUI(user, bankAccountInformation);
                setVisible(false);
                depositMoneyGUI.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "You have no existing bank account");
            }
        });

        bindAccountButton.addActionListener(e -> {
//            bankAccountInformationDAO.bindBankAccount(bankAccountInformation);
            BindBankAccountGUI bindBankAccountGUI = new BindBankAccountGUI(user, bankAccountInformationDAO.retrieveBankAccountInformation(bankAccountInformation));
            setVisible(false);
            bindBankAccountGUI.setVisible(true);

        });

        transactionHistoryButton.addActionListener(e -> {

                TransactionHistoryGUI transactionHistoryGUI = new TransactionHistoryGUI(user, Long.parseLong(bankAccountComboBox.getSelectedItem().toString()));
//                UserHomeGUI userHomeGUI = new UserHomeGUI(bankAccountInformation.getUser_id());
                setVisible(false);
                transactionHistoryGUI.setVisible(true);
        });

        changePasswordButton.addActionListener(e -> {
            ChangePasswordGUI changePasswordGUI = new ChangePasswordGUI(user);
            setVisible(false);
            changePasswordGUI.setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            setVisible(false);
            loginGUI.setVisible(true);

        });

        bankAccountComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankAccountInformation bankAccountInformationComboBox = new BankAccountInformation(Long.parseLong(bankAccountComboBox.getSelectedItem().toString()), user.getUser_id());
                currentBalanceLabel.setText(String.valueOf(bankAccountInformationDAO.retrieveBankAccountInformation(bankAccountInformationComboBox).getAccount_balance()));

            }
        });
    }

}
