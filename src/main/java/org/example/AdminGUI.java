package org.example;

import org.example.model.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminGUI extends JFrame {
    private JPanel AdminPanel;
    private JButton createBankAccountButton;
    private JButton goBackButton;
    private JLabel employeeIDValue;
    private JLabel employeeNameValue;
    private JButton seeTransactionsButton;

    public AdminGUI(Admin admin) {
        setContentPane(AdminPanel);
        setTitle("Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);

        employeeIDValue.setText(String.valueOf(admin.getAdmin_id()));
        employeeNameValue.setText(admin.getAdmin_first_name() + " " + admin.getAdmin_middle_name() + " " + admin.getAdmin_last_name());

//        adminDAO.fillAdminInformation(admin);

        createBankAccountButton.addActionListener(e -> {
            CreateBankAccountGUI createBankAccountGUI = new CreateBankAccountGUI(admin);
            setVisible(false);
            createBankAccountGUI.setVisible(true);

        });

        goBackButton.addActionListener(e -> {
            AdminLoginGUI adminLoginGUI = new AdminLoginGUI();
            setVisible(false);
            adminLoginGUI.setVisible(true);
        });


        seeTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankTransactionsGUI bankTransactionsGUI = new BankTransactionsGUI(admin);
                setVisible(false);
                bankTransactionsGUI.setVisible(true);
            }
        });
    }

//    private void fillAdminInformation(Admin admin) {
//        //Retrieve Admin Info
//        String retrieve_admin_info = "SELECT admin_id, admin_first_name, admin_middle_name, admin_last_name " +
//                "FROM admin_table " +
//                "WHERE admin_id = ?;";
//
//        try {
//
//            assert conn != null;
//            PreparedStatement pst = conn.prepareStatement(retrieve_admin_info);
//
//            pst.setInt(1, admin.getAdmin_id());
//            ResultSet rs = pst.executeQuery();
//
//            while(rs.next()) {
//                String admin_id = rs.getString("admin_id");
//                String fullName = rs.getString("admin_first_name") + " " +
//                        rs.getString("admin_middle_name") + " " +
//                        rs.getString("admin_last_name");
//
//                employeeIDValue.setText(admin_id);
//                employeeNameValue.setText(fullName);
//
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }

}

