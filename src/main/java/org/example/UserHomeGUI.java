package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserHomeGUI extends JFrame {
    private JPanel UserHomePanel;
    private JButton sendMoneyButton;
    private JButton transactionHistoryButton;
    private JLabel userNameLabel;
    private JLabel emailLabel;
    private JButton changePasswordButton;
    private JPanel userInformation;
    private JPanel userInformationValues;
    private JLabel fullNameLabel;
    private JLabel currentBalanceLabel;
    private JButton bindAccountButton;

    Connection conn = DBConnector.getConnection();

    public UserHomeGUI(int id) {
        setContentPane(UserHomePanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480,500);
        setLocationRelativeTo(null);
        setVisible(true);

        //Fills valid user information based on the user id arguments
        fillInformation(id);

        sendMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String determine_existing_bank_account_query = "SELECT * " +
                        "FROM bank_account_information " +
                        "WHERE user_id = ?";

                try {
                    PreparedStatement pst = conn.prepareStatement(determine_existing_bank_account_query);
                    pst.setInt(1, id);

                    ResultSet rs = pst.executeQuery();

                    if(!rs.next()) {
                        JOptionPane.showMessageDialog(null, "No bound account.");
                    } else {
                        do {
                            Long current_tracking_number = rs.getLong("account_tracking_number");
                            SendMoneyGUI sendMoneyGUI = new SendMoneyGUI(id, current_tracking_number);
                            setVisible(false);
                            sendMoneyGUI.setVisible(true);

                        } while(rs.next());
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        bindAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BindAccountGUI bindAccountGUI = new BindAccountGUI();
                int result = JOptionPane.showConfirmDialog(null, bindAccountGUI.BindAccountPanel,
                        "Enter your details",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if(result == JOptionPane.OK_OPTION) {

                    Long account_number = Long.parseLong(bindAccountGUI.accountNumberField.getText());
                    String first_name = bindAccountGUI.firstNameField.getText();
                    String middle_name = bindAccountGUI.middleNameField.getText();
                    String last_name = bindAccountGUI.lastNameField.getText();
                    String mobile_number = bindAccountGUI.mobileNumberField.getText();

                    String bank_account_query  = "SELECT account_tracking_number," +
                            "account_holder_first_name, " +
                            "account_holder_middle_name, " +
                            "account_holder_last_name, " +
                            "account_holder_mobile_number  " +
                            "FROM bank_account_information " +
                            "WHERE account_tracking_number = ? AND " +
                            "account_holder_first_name = ? AND " +
                            "account_holder_middle_name = ? AND " +
                            "account_holder_last_name = ? AND " +
                            "account_holder_mobile_number = ?";



                    if (bindAccountGUI.accountNumberField.getText().isEmpty() ||
                            bindAccountGUI.firstNameField.getText().isEmpty() ||
                            bindAccountGUI.middleNameField.getText().isEmpty() ||
                            bindAccountGUI.lastNameField.getText().isEmpty() ||
                            bindAccountGUI.mobileNumberField.getText().isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Please fill all the fields.");

                    } else {
                        try {
                            PreparedStatement pst = conn.prepareStatement(bank_account_query);

                            pst.setLong(1, account_number);
                            pst.setString(2, first_name);
                            pst.setString(3, middle_name);
                            pst.setString(4, last_name);
                            pst.setString(5, mobile_number);

                            ResultSet rs = pst.executeQuery();

                            if (!rs.next()) {
                                JOptionPane.showMessageDialog(null, "No account exist");
                            } else {

                                String bind_user_id = "UPDATE bank_account_information " +
                                        "SET user_id = ?" +
                                        "WHERE bank_account_information.account_tracking_number = ?";

                                PreparedStatement pst2 = conn.prepareStatement(bind_user_id);
                                pst2.setInt(1, id);
                                pst2.setLong(2, account_number);

                                pst2.executeUpdate();

                                JOptionPane.showMessageDialog(null, "Account successfully bind");
                            }


                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            }
        });
    }

    private void fillInformation(int id) {

        String retrieveUserInfo = "SELECT * " +
                "FROM user_account_table " +
                "WHERE user_id = ?;";

        try {

            PreparedStatement pst = conn.prepareStatement(retrieveUserInfo);

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                String full_name = rs.getString("user_first_name") + " " +
                        rs.getString("user_middle_name") + " " +
                        rs.getString("user_last_name");
                String user_name = rs.getString("user_name");
                String email = rs.getString("user_email");

                fullNameLabel.setText(full_name);
                userNameLabel.setText(user_name);
                emailLabel.setText(email);

            }

            String retrieveUserBalance = "SELECT account_balance " +
                    "FROM bank_account_information " +
                    "WHERE user_id = ?;";

            PreparedStatement pst2 = conn.prepareStatement(retrieveUserBalance);
            pst2.setInt(1, id);
            ResultSet rs2 = pst2.executeQuery();

            if(!rs2.next()) {
                    JOptionPane.showMessageDialog(null, "No bound account");
            } else {
                do {
                    Double currentBalance = rs2.getDouble("account_balance");
                    currentBalanceLabel.setText(""+currentBalance);
                } while(rs2.next());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new UserHomeGUI(1);
    }



}
