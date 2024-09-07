package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginGUI extends JFrame{
    private JPanel loginPanel;
    private JTextField loginUserField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton adminButton;

    //Insert user info to user_credentials table

    public LoginGUI() {
        setContentPane(loginPanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(e -> {
            //If function determining if user field or password field is empty
            if (loginUserField.getText().isEmpty() || loginPasswordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both username and password");
            } else {

                //Retrieve all values from the login text fields.
                String user_temp = loginUserField.getText();
                String passwd_temp = loginPasswordField.getText();
                String loginQuery = "SELECT * " +
                        "FROM user_account_table " +
                        "WHERE user_name = ? and user_password = ?";

                //Execute login script with the values from login text fields
                try {
                    Connection conn = DBConnector.getConnection();
                    PreparedStatement pst = conn.prepareStatement(loginQuery);

                    pst.setString(1, user_temp);
                    pst.setString(2, passwd_temp);
                    ResultSet rs = pst.executeQuery();

                    //Retrieve values from columns
                    while(rs.next()) {
                        int id = rs.getInt("user_id");
                        String user = rs.getString("user_name");
                        String passwd = rs.getString("user_password");

                        //Checks login text fields if values are equal to the login query result
                        if(user_temp.equals(user) && passwd_temp.equals(passwd)) {
                            JOptionPane.showMessageDialog(null, "Login Successful");

                            //Opens UserHomeGui
                            UserHomeGUI user_home = new UserHomeGUI(id);
//                                new UserHomeGUI(id);
                            setVisible(false);
                            user_home.setVisible(true);

                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Credentials");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        registerButton.addActionListener(e -> {
            RegisterGUI registerGUI = new RegisterGUI();
            setVisible(false);
            registerGUI.setVisible(true);
        });

        adminButton.addActionListener(e -> {
            AdminLoginGUI adminLoginGUI = new AdminLoginGUI();
            setVisible(false);
            adminLoginGUI.setVisible(true);
        });

    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
