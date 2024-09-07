package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangePasswordGUI extends JFrame {
    private JPanel changePasswordPanel;
    private JButton changePasswordButton;
    private JButton goBackButton;
    private JTextField oldPasswordField;
    private JTextField newPasswordField;
    private JTextField confirmPassword;

    Connection conn = DBConnector.getConnection();

    public ChangePasswordGUI(int user_id) {
        setContentPane(changePasswordPanel);
        setTitle("User Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        changePasswordButton.addActionListener(e -> {

            String find_user_password_query = "SELECT user_password " +
                    "FROM user_account_table " +
                    "WHERE user_id = ?;";

            try {
                PreparedStatement pst2 = conn.prepareStatement(find_user_password_query);
                pst2.setInt(1, user_id);
                ResultSet rs = pst2.executeQuery();

                while(rs.next()) {
                    String user_password = rs.getString("user_password");
                    if (user_password.equals(oldPasswordField.getText())) {
                        String update_password_query = "UPDATE user_account_table " +
                                "SET user_password = ? " +
                                "WHERE user_id = ?;";

                        if (isEmptyField(oldPasswordField.getText()) || isEmptyField(newPasswordField.getText()) || isEmptyField(confirmPassword.getText())) {
                            JOptionPane.showMessageDialog(changePasswordPanel, "Old Password and New Password Are Empty");
                        } else if (oldPasswordField.getText().equals(confirmPassword.getText()) || oldPasswordField.getText().equals(newPasswordField.getText())) {
                            JOptionPane.showMessageDialog(changePasswordPanel, "Old Password and New Password Are Equal");
                        } else if (!newPasswordField.getText().equals(confirmPassword.getText())) {
                            JOptionPane.showMessageDialog(changePasswordPanel, "New Password and Confirm Password Are Not Equal");
                        } else {
                            try {
                                PreparedStatement pst = conn.prepareStatement(update_password_query);
                                pst.setString(1, newPasswordField.getText());
                                pst.setInt(2, user_id);
                                pst.executeUpdate();

                                JOptionPane.showMessageDialog(changePasswordPanel, "Password Updated Successfully");

                                // Clear textfields
                                oldPasswordField.setText("");
                                newPasswordField.setText("");
                                confirmPassword.setText("");

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(changePasswordPanel, ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(changePasswordPanel, "Invalid Old Password");
                    }

                }

            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(null, ex2);
            }


        });

        goBackButton.addActionListener(e -> {
            UserHomeGUI userHomeGUI = new UserHomeGUI(user_id);
            setVisible(false);
            userHomeGUI.setVisible(true);
        });
    }

    private boolean isEmptyField(String text) {
        return text.trim().isEmpty();
    }

}
