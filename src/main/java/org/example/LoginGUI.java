package org.example;

import org.example.model.User;
import org.example.dao.UserDAO;

import javax.swing.*;

public class LoginGUI extends JFrame{
    private JPanel loginPanel;
    private JTextField loginUserField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton adminButton;

    UserDAO userDAO = new UserDAO();

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
                //Execute login script with the values from login text fields
                try {
                    // calls
                    User user = new User(loginUserField.getText().trim(), loginPasswordField.getText().trim());
                    UserHomeGUI userHomeGUI = new UserHomeGUI(userDAO.loginUser(user));
                    setVisible(false);
                    userHomeGUI.setVisible(true);

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
