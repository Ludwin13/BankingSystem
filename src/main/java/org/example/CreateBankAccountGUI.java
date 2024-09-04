package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateBankAccountGUI extends JFrame {
    private JButton createBankAccountButton;
    private JButton backButton;
    private JComboBox validIDComboBox;
    private JTextField depositAmountField;
    private JPanel createBankAccountPanel;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField addressLine1Field;
    private JTextField addressLine2Field;
    private JTextField mobileNumberField;
    private static int global_latest_tracking_num;

    public CreateBankAccountGUI() {
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
                    // Customer Identifer - 8 digits
                    // for example:
                        // Branch Code: 1012
                        // Customer ID: 1
                        // Tracking Number: 101200000001

                String retrieve_user_id = "SELECT MAX(account_tracking_number) AS account_tracking_number " +
                        "FROM bank_account_information;";

                try {

                    Connection conn = DBConnector.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(retrieve_user_id);
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                        int current_tracking_number = rs.getInt("account_tracking_number");

                        System.out.println(current_tracking_number);

                        String create_bank_account_query = "INSERT INTO bank_account_information (" +
                                "account_tracking_number, account_balance, date_created) " +
                                "VALUES (?, ?, ?)";

                        PreparedStatement stmt2 = conn.prepareStatement(create_bank_account_query);
                        stmt2.setInt(1, current_tracking_number + 1);
                        stmt2.setDouble(2, Double.parseDouble(depositAmountField.getText()));
                        stmt2.setString(3, ft.format(new Date()));

                        String insert_user_information = "INSERT INTO bank_account_holder_information(" +
                                "account_tracking_number, " +
                                "account_holder_first_name, " +
                                "account_holder_middle_name, " +
                                "account_holder_last_name, " +
                                "account_holder_mobile_number, " +
                                "account_holder_address, " +
                                "account_holder_valid_id)" +
                                "VALUES (?, ?, ?, ?, ?, ?, ?);";


                        PreparedStatement stmt3 = conn.prepareStatement(insert_user_information);
                        stmt3.setInt(1, current_tracking_number + 1);
                        stmt3.setString(2, firstNameField.getText());
                        stmt3.setString(3, middleNameField.getText());
                        stmt3.setString(4, lastNameField.getText());
                        stmt3.setString(5, mobileNumberField.getText());
                        stmt3.setString(6, addressLine1Field.getText() + " " + addressLine2Field.getText());
                        stmt3.setString(7, validIDComboBox.getSelectedItem().toString());

                        stmt3.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Account Created");

                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex + " 1st query");
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

    public static void main(String[] args) {
        new CreateBankAccountGUI();
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
