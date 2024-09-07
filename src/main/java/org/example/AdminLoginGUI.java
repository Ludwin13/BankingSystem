package org.example;

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

    public AdminLoginGUI() {
        setContentPane(AdminLoginPanel);
        setTitle("Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);


        loginButton.addActionListener(e -> {
            String userName = userNameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String loginQuery = "SELECT * " +
                    "FROM admin_table " +
                    "WHERE admin_user_name = ? AND admin_password = ?";

            try {
                Connection conn = DBConnector.getConnection();
                assert conn != null;
                PreparedStatement ps = conn.prepareStatement(loginQuery);

                ps.setString(1, userName);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    int admin_id = rs.getInt("admin_id");
                    String user = rs.getString("admin_user_name");
                    String pass = rs.getString("admin_password");

                    if(user.equals(userName) && pass.equals(password)) {
                        JOptionPane.showMessageDialog(AdminLoginPanel, "Admin Login Successful");
                        AdminGUI adminGUI = new AdminGUI(admin_id);

                        setVisible(false);
                        adminGUI.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(AdminLoginPanel, "Wrong Password");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });

        backButton.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            setVisible(false);
            loginGUI.setVisible(true);

        });
    }
}
