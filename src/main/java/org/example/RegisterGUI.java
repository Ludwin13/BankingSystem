package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setSize(720,800);
        setLocationRelativeTo(null);
        setVisible(true);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //checks all fields if empty
                if (firstNameField.getText().equals("") || lastNameField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please both first and last name");
                } else if (emailField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter email");
                } else if (passwordField1.getText().equals("") || passwordField2.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter password");
                } else if (userNameField.getText().equals("")) {
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

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI loginGUI = new LoginGUI();
                setVisible(false);
                loginGUI.setVisible(true);
            }
        });
    }
    public static void main(String[] args) {
        new RegisterGUI();
    }
}
