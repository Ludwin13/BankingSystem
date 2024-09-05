package org.example;
import javax.swing.*;
import java.sql.*;

public class DBConnector {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:banking_system.db");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }
}
