package org.example;

import org.example.dao.BankAccountInformationDAO;
import org.example.model.BankAccountInformation;
import org.example.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BindBankAccountGUI extends JFrame {
    private JTextField accountNumberFIeld;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField middleNameField;
    private JTextField mobileNumberField;
    private JButton clearFieldsButton;
    private JButton bindBankAccountButton;
    private JButton goBackButton;
    private JPanel BindBankAccountPanel;

    BankAccountInformationDAO bankAccountInformationDAO = new BankAccountInformationDAO();

    public BindBankAccountGUI(User user, BankAccountInformation bankAccountInformation) {
        setContentPane(BindBankAccountPanel);
        setTitle("Bind Bank Account");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserHomeGUI userHomeGUI = new UserHomeGUI(user);
                setVisible(false);
                userHomeGUI.setVisible(true);
            }
        });

        clearFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        bindBankAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = bankAccountInformationDAO.bindBankAccount(bankAccountInformation, Long.parseLong(accountNumberFIeld.getText()),
                        firstNameField.getText(),
                        middleNameField.getText(),
                        lastNameField.getText(),
                        mobileNumberField.getText());
                if (!status) {
                    JOptionPane.showMessageDialog(null, "No Account Exists");
                } else {
                    JOptionPane.showMessageDialog(null, "Account successfully bind");
                }
            }
        });
    }

    private void clearFields() {
        accountNumberFIeld.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        middleNameField.setText("");
        mobileNumberField.setText("");
    }
}
