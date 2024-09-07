package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.text.SimpleDateFormat;

public class RegisterGUI extends JFrame {
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField userNameField;
    private JTextField emailField;
    private JButton registerButton;
    private JPanel registerPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton goBackButton;
    private JTextField middleNameField;

    public RegisterGUI() {
        setContentPane(registerPanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        registerButton.addActionListener(e -> {
            //checks all fields if empty
            if (isEmptyField(firstNameField.getText()) || isEmptyField(lastNameField.getText())) {
                JOptionPane.showMessageDialog(null, "Please enter both first and last name");
            } else if (isEmptyField(emailField.getText())) {
                JOptionPane.showMessageDialog(null, "Please enter email");
            } else if (isEmptyField(passwordField1.getText()) || isEmptyField(passwordField2.getText())) {
                JOptionPane.showMessageDialog(null, "Please enter password");
            } else if (isEmptyField(userNameField.getText())) {
                JOptionPane.showMessageDialog(null, "Please enter username");;
            } else if (!passwordField1.getText().trim().equals(passwordField2.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            } else {
                //Add fields to database
                try {
                    String create_user_account = "INSERT INTO user_account_table (" +
                            "user_name, " +
                            "user_first_name, " +
                            "user_middle_name, " +
                            "user_last_name, " +
                            "user_email," +
                            "user_password, " +
                            "date_created) " +
                            "VALUES (?,?,?,?,?,?,?);";

                    Connection conn = DBConnector.getConnection();
                    PreparedStatement pst = conn.prepareStatement(create_user_account);
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

                    pst.setString(1, userNameField.getText().trim());
                    pst.setString(2, firstNameField.getText().trim());
                    pst.setString(3, middleNameField.getText().trim());
                    pst.setString(4, lastNameField.getText().trim());
                    pst.setString(5, emailField.getText().trim());
                    pst.setString(6, passwordField1.getText().trim());
                    pst.setString(7, ft.format(new Date()));

                    //Insert Username, and Password to user_account_table
                    pst.executeUpdate();
                    conn.close();
                    JOptionPane.showMessageDialog(null, "Successfully Registered");
                    LoginGUI loginGUI = new LoginGUI();
                    setVisible(false);
                    loginGUI.setVisible(true);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        goBackButton.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            setVisible(false);
            loginGUI.setVisible(true);
        });
    }

    private boolean isEmptyField(String fieldText) {
        return fieldText.trim().isEmpty();
    }

}
