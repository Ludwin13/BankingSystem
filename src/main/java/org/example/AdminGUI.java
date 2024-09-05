package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminGUI extends JFrame {
    private JPanel AdminPanel;
    private JButton createBankAccountButton;
    private JButton button2;
    private JLabel employeeIDValue;
    private JLabel employeeNameValue;

    public AdminGUI(int admin_id) {
        setContentPane(AdminPanel);
        setTitle("Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,800);
        setLocationRelativeTo(null);
        setVisible(true);

        fillAdminInformation(admin_id);

        createBankAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateBankAccountGUI createBankAccountGUI = new CreateBankAccountGUI(admin_id);
                setVisible(false);
                createBankAccountGUI.setVisible(true);

            }
        });
    }

    private void fillAdminInformation(int id) {
        String retrieveAdminInfo = "SELECT A_IT.admin_id, A_IT.first_name, A_IT.middle_name, A_IT.last_name" +
                "FROM admin_info_table AS A_IT" +
                "JOIN admin_table AS A_T" +
                "ON A_IT.admin_id = A_T.admin_id" +
                "WHERE A_IT.admin_id = ? AND A_T.admin_id = ?;";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(retrieveAdminInfo);

            pst.setInt(1, id);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                String admin_id = rs.getString("admin_id");
                String fullName = rs.getString("first_name") + " " +
                        rs.getString("middle_name") + " " +
                        rs.getString("last_name");

                employeeIDValue.setText(admin_id);
                employeeNameValue.setText(fullName);

            }
        } catch (Exception ex) {

        }



    }
}
