package org.example;

import org.example.dao.AdminDAO;
import org.example.model.Admin;

import javax.swing.*;

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
    private JButton CLEARFIELDSButton;

    AdminDAO adminDAO = new AdminDAO();

    public CreateBankAccountGUI(Admin admin) {
        setContentPane(createBankAccountPanel);
        setTitle("Create Bank Account");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1440,720);
        setLocationRelativeTo(null);
        setVisible(true);

        //fill Valid ID Combo Box with list of Valid ID
        fillComboBox();

        createBankAccountButton.addActionListener(e -> {
            if (mobileNumberField.getText().length() != 11) {
                JOptionPane.showMessageDialog(CreateBankAccountGUI.this, "Enter valid mobile number starting with '09' ");
            } else if (isEmptyField(firstNameField.getText()) || isEmptyField(middleNameField.getText()) || isEmptyField(lastNameField.getText())) {
                JOptionPane.showMessageDialog(CreateBankAccountGUI.this, "Please enter both first name and last names");
            } else if (isEmptyField(addressLine1Field.getText()) && isEmptyField(addressLine2Field.getText())) {
                JOptionPane.showMessageDialog(CreateBankAccountGUI.this, "Please enter both address line 1 and address line 2");
            } else {
                try {
                    boolean create_status = adminDAO.createBankAccount(Double.parseDouble(depositAmountField.getText()),
                            firstNameField.getText(), middleNameField.getText(), lastNameField.getText(),
                            mobileNumberField.getText(), addressLine1Field.getText(), addressLine2Field.getText(),
                            validIDComboBox.getSelectedItem().toString());
                    if(!create_status) {
                        JOptionPane.showMessageDialog(CreateBankAccountGUI.this, "Account creation failed");
                    } else {
                        JOptionPane.showMessageDialog(CreateBankAccountGUI.this, "Account created");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        backButton.addActionListener(e -> {
            AdminGUI adminGUI = new AdminGUI(admin);
            setVisible(false);
            adminGUI.setVisible(true);
        });

        CLEARFIELDSButton.addActionListener(e -> {
            depositAmountField.setText("");
            lastNameField.setText("");
            middleNameField.setText("");
            firstNameField.setText("");
            addressLine1Field.setText("");
            addressLine2Field.setText("");
            mobileNumberField.setText("");
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

    private boolean isEmptyField(String textField) {
        return textField.trim().isEmpty();
    }

}
