package org.example;

import org.example.dao.AdminDAO;
import org.example.model.Admin;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginGUI extends JFrame {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JPanel AdminLoginPanel;

    AdminDAO adminDAO = new AdminDAO();

    public AdminLoginGUI() {
        setContentPane(AdminLoginPanel);
        setTitle("Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(e -> {

            Admin admin = new Admin(userNameField.getText(), String.valueOf(passwordField.getPassword()));
            Admin loginStatus = adminDAO.loginAdmin(admin);
            if (loginStatus != null) {
                AdminGUI adminGUI = new AdminGUI(adminDAO.loginAdmin(admin));
                setVisible(false);
                adminGUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Login Failed");
            }

        });

        backButton.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            setVisible(false);
            loginGUI.setVisible(true);

        });
    }
}
