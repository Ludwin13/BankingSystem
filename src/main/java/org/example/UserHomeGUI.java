package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserHomeGUI extends JFrame {
    private JPanel UserHomePanel;
    private JButton createBankAccountButton;
    private JButton sendMoneyButton;
    private JButton transactionHistoryButton;
    private JLabel userNameLabel;
    private JLabel emailLabel;
    private JButton changePasswordButton;
    private JPanel userInformation;
    private JPanel userInformationValues;
    private JLabel fullNameLabel;
    private JLabel currentBalanceLabel;

    public UserHomeGUI(int id) {
        setContentPane(UserHomePanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,800);
        setLocationRelativeTo(null);
        setVisible(true);

        //Fills valid user information based on the user id arguments
        fillInformation(id);


    }

    private void fillInformation(int id) {
        String retrieveUserInfo = "SELECT * " +
                "FROM user_account_table " +
                "WHERE user_id = ?";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(retrieveUserInfo);

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                String full_name = rs.getString("first_name") + " " + rs.getString("last_name");
                String user_name = rs.getString("user_name");
                String email = rs.getString("user_email");

                fullNameLabel.setText(full_name);
                userNameLabel.setText(user_name);
                emailLabel.setText(email);


            }
        } catch (Exception ex) {

        }
    }

    public static void main(String[] args) {

    }

}
