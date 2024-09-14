package org.example.dao;

import org.example.DBConnector;
import org.example.model.Admin;
import org.example.model.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDAO {
    private Connection conn = DBConnector.getConnection();

    public void registerUser(User user) {
        String create_user_account = "INSERT INTO user_account_table (" +
                "user_name, " +
                "user_first_name, " +
                "user_middle_name, " +
                "user_last_name, " +
                "user_email," +
                "user_password, " +
                "date_created) " +
                "VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = conn.prepareStatement(create_user_account);
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

            pst.setString(1, user.getUser_name());
            pst.setString(2, user.getUser_first_name());
            pst.setString(3, user.getUser_middle_name());
            pst.setString(4, user.getUser_last_name());
            pst.setString(5, user.getUser_email());
            pst.setString(6, user.getUser_password());
            pst.setString(7, ft.format(new Date()));

            //Insert Username, and Password to user_account_table
            pst.executeUpdate();

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }

    public User loginUser(User user) {
        String loginQuery = "SELECT * " +
                "FROM user_account_table " +
                "WHERE user_name = ? and user_password = ?";

        try {
            PreparedStatement pst1 = conn.prepareStatement(loginQuery);

            pst1.setString(1, user.getUser_name());
            pst1.setString(2, user.getUser_password());
            ResultSet rs1 = pst1.executeQuery();

            //Retrieve values from columns
            while(rs1.next()) {
                int id = rs1.getInt("user_id");
                String user_name = rs1.getString("user_name");
                String passwd = rs1.getString("user_password");

                //Checks login text fields if values are equal to the login query result
                if(user.getUser_name().equals(user_name) && user.getUser_password().equals(passwd)) {
                    user.setUser_id(rs1.getInt("user_id"));
                    user.setUser_password(rs1.getString("user_password"));
                    user.setUser_email(rs1.getString("user_email"));
                    user.setUser_first_name(rs1.getString("user_first_name"));
                    user.setUser_middle_name(rs1.getString("user_middle_name"));
                    user.setUser_last_name(rs1.getString("user_last_name"));

                    JOptionPane.showMessageDialog(null, "Successfully logged in");
                    conn.close();
                    return user;
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password");
                    conn.close();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return user;
    }

    public String findUserInformation(User user) {
        String retrieveUserInfo = "SELECT * " +
                "FROM user_account_table " +
                "WHERE user_id = ?;";

        try {
            PreparedStatement pst2 = conn.prepareStatement(retrieveUserInfo);
            pst2.setInt(1, user.getUser_id());
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()) {
                String full_name = rs2.getString("user_first_name") + " " +
                        rs2.getString("user_middle_name") + " " +
                        rs2.getString("user_last_name");
                String user_name = rs2.getString("user_name");
                String email = rs2.getString("user_email");

                String user_information_toString = full_name + ":" + user_name + ":" + email;

                return  user_information_toString;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

}
