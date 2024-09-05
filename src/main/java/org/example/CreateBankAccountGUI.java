package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateBankAccountGUI extends JFrame {
    private JButton createBankAccountButton;
    private JButton backButton;
    private JComboBox validIDComboBox;
    private JTextField depositAmountField;
    private JPanel createBankAccountPanel;
    private JTextField lastNameField;
    private JTextField middleNameField;
    private JTextField firstNameField;
    private JTextField addressLine1Field;
    private JTextField addressLine2Field;
    private JTextField mobileNumberField;

    public CreateBankAccountGUI(int admin_id) {
        setContentPane(createBankAccountPanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,800);
        setLocationRelativeTo(null);
        setVisible(true);

        //fill Valid ID Combo Box with list of Valid ID
        fillComboBox();

        createBankAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Create a 12-digit account tracking number
                    // Branch Code - 4 digits
                    // Customer Identifier - 8 digits
                    // for example:
                        // Branch Code: 1012
                        // Customer ID: 1
                        // Tracking Number: 101200000001
                if (mobileNumberField.getText().length() != 11) {
                    JOptionPane.showMessageDialog(CreateBankAccountGUI.this, "Enter valid mobile number starting with '09' ");
                } else {
                    try {
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                        Connection conn = DBConnector.getConnection();

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

                        PreparedStatement stmt = conn.prepareStatement(create_bank_account_query);
                        stmt.setDouble(1, Double.parseDouble(depositAmountField.getText()));
                        stmt.setString(2, firstNameField.getText());
                        stmt.setString(3, middleNameField.getText());
                        stmt.setString(4, lastNameField.getText());
                        stmt.setString(5, mobileNumberField.getText());
                        stmt.setString(6, addressLine1Field.getText() + " " + addressLine2Field.getText());
                        stmt.setString(7, validIDComboBox.getSelectedItem().toString());
                        stmt.setString(8, ft.format(new Date()));

                        stmt.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Account Created");

                        conn.close();
                        AdminGUI adminGUI = new AdminGUI(admin_id);
                        setVisible(false);
                        adminGUI.setVisible(true);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String test = "1020202020202";
                int lastString = Integer.parseInt(test.substring(3, test.length()));
                System.out.println(String.valueOf(lastString));
                System.out.println(String.valueOf(lastString + 1));

            }
        });
    }


    private void fillComboBox() {
        validIDComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
                "Driver's License",
                "Passport",
                "Philippine Postal ID",
                "Senior Citizen ID",
                "PRC ID",
                "SSS / GSIS ID",
                "TIN ID",
                "Voter’s ID",
                "NBI Clearance",
                "Bank Account",
                "Police Clearance with dry seal of PNP",
                "Barangay Certification with dry seal from the Barangay where it was issued",
                "Health Insurance Card ng Bayan issued by PhilHealth Insurance Corporation",
                "Overseas Workers Welfare Administration (OWWA)",
                "Overseas Filipino Workers (OFW) ID",
                "Seaman’s Book",
                "Government Office and GOCC ID (e.g. Armed Forces of the Philippines [AFP], Home Development Mutual Fund [HDMF] IDs)",
                "Certification from the National Council for the Welfare of Disabled Persons (NCWDP) or Person with Disability Identification Card issued by the National Council of Disability Affairs (NCDA)",
                "Department of Social Welfare and Development (DSWD) Certification",
                "Integrated Bar of the Philippines (IBP) ID",
                "Company IDs issued by private entities or institutions registered with or supervised or regulated by BSP, SEC or IC.Maritime Industry Authority (MARINA) ID)"
        }));
    }


}
