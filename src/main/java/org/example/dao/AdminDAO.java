package org.example.dao;

import org.example.DBConnector;
import org.example.model.Admin;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminDAO {
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

    public Admin loginAdmin(Admin admin) {
//        String userName = userNameField.getText();
//        String password = String.valueOf(passwordField.getPassword());
        String loginQuery = "SELECT * " +
                "FROM admin_table " +
                "WHERE admin_user_name = ? AND admin_password = ?";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(loginQuery);

            ps.setString(1, admin.getAdmin_user_name());
            ps.setString(2, admin.getAdmin_password());
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                do {
                    String user = rs.getString("admin_user_name");
                    String pass = rs.getString("admin_password");

                    if (user.equals(admin.getAdmin_user_name()) && pass.equals(admin.getAdmin_password())) {
                        admin.setAdmin_id(rs.getInt("admin_id"));
                        admin.setAdmin_user_name(rs.getString("admin_user_name"));
                        admin.setAdmin_first_name(rs.getString("admin_first_name"));
                        admin.setAdmin_middle_name(rs.getString("admin_middle_name"));
                        admin.setAdmin_last_name(rs.getString("admin_last_name"));
                        admin.setAdmin_password(rs.getString("admin_password"));
                        admin.setDate_created(rs.getString("date_created"));

//                        JOptionPane.showMessageDialog(null, "Admin Login Successful");
                        conn.close();
                        return admin;
                    } else {
                        conn.close();
                        // JOptionPane.showMessageDialog(null, "Wrong Password");
                        return null;
                    }
                } while (rs.next());
            }


//            while(rs.next()) {
//                String user = rs.getString("admin_user_name");
//                String pass = rs.getString("admin_password");
//
//                if(user.equals(admin.getAdmin_user_name()) && pass.equals(admin.getAdmin_password())) {
//                    admin.setAdmin_id(rs.getInt("admin_id"));
//                    admin.setAdmin_user_name(rs.getString("admin_user_name"));
//                    admin.setAdmin_first_name(rs.getString("admin_first_name"));
//                    admin.setAdmin_middle_name(rs.getString("admin_middle_name"));
//                    admin.setAdmin_last_name(rs.getString("admin_last_name"));
//                    admin.setAdmin_password(rs.getString("admin_password"));
//                    admin.setDate_created(rs.getString("date_created"));
//
//                    JOptionPane.showMessageDialog(null, "Admin Login Successful");
//                    conn.close();
//
//                    return admin;
//                } else {
//                    JOptionPane.showMessageDialog(null, "Wrong Password");
//                }
//            }
        } catch (Exception ex) {
            return null;
//            JOptionPane.showMessageDialog(null, ex);
        }
//        return admin;
    }

    public boolean createBankAccount(double depositAmount, String firstName, String middleName, String lastName, String mobileNumber,
                                  String addressLine1, String addressLine2, String validID) {
        try {

            String create_bank_account_query = "INSERT INTO bank_account_information (" +
                    "account_balance, " +
                    "account_holder_first_name, " +
                    "account_holder_middle_name, " +
                    "account_holder_last_name, " +
                    "account_holder_mobile_number," +
                    "account_holder_address, " +
                    "account_holder_valid_id, " +
                    "date_created) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = DBConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(create_bank_account_query);
            stmt.setDouble(1, depositAmount);
            stmt.setString(2, firstName);
            stmt.setString(3, middleName);
            stmt.setString(4, lastName);
            stmt.setString(5, mobileNumber);
            stmt.setString(6, addressLine1 + " " + addressLine2);
            stmt.setString(7, validID);
            stmt.setString(8, ft.format(new Date()));

            stmt.executeUpdate();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }

}
