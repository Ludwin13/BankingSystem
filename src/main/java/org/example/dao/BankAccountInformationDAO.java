package org.example.dao;

import org.example.*;
import org.example.model.BankAccountInformation;
import org.example.model.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankAccountInformationDAO {

    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

    public List<BankAccountInformation> retrieveUserBalance(User user) {
        List<BankAccountInformation> bankAccountInformationList = new ArrayList<>();
        String retrieveUserBalance = "SELECT * " +
                "FROM bank_account_information " +
                "WHERE user_id = ?;";
        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(retrieveUserBalance);
            pst.setInt(1, user.getUser_id());
            ResultSet rs = pst.executeQuery();

            if(!rs.next()) {
                return bankAccountInformationList;
            } else {
                do {
                    // currentBalanceLabel.setText(""+currentBalance);
                    BankAccountInformation bankAccountInformation = new BankAccountInformation();
                    bankAccountInformation.setAccount_tracking_number(rs.getLong("account_tracking_number"));
                    bankAccountInformation.setUser_id(rs.getInt("user_id"));
                    bankAccountInformation.setAccount_balance(rs.getDouble("account_balance"));
                    bankAccountInformation.setAccount_holder_first_name(rs.getString("account_holder_first_name"));
                    bankAccountInformation.setAccount_holder_middle_name(rs.getString("account_holder_middle_name"));
                    bankAccountInformation.setAccount_holder_last_name(rs.getString("account_holder_last_name"));
                    bankAccountInformation.setAccount_holder_address(rs.getString("account_holder_address"));
                    bankAccountInformationList.add(bankAccountInformation);

                } while(rs.next());
                conn.close();
                return bankAccountInformationList;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        return bankAccountInformationList;
    }

    public BankAccountInformation retrieveBankAccountInformation(BankAccountInformation bankAccountInformation) {
        String retrieve_balance = "SELECT * " +
                "FROM bank_account_information " +
                "WHERE account_tracking_number = ? AND " +
                "user_id = ?";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(retrieve_balance);
            ps.setLong(1, bankAccountInformation.getAccount_tracking_number());
            ps.setInt(2, bankAccountInformation.getUser_id());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    bankAccountInformation.setAccount_balance(rs.getDouble("account_balance"));
                    bankAccountInformation.setAccount_holder_first_name(rs.getString("account_holder_first_name"));
                    bankAccountInformation.setAccount_holder_middle_name(rs.getString("account_holder_middle_name"));
                    bankAccountInformation.setAccount_holder_last_name(rs.getString("account_holder_last_name"));
                    bankAccountInformation.setAccount_holder_address(rs.getString("account_holder_address"));
                } while (rs.next());
                conn.close();
                return bankAccountInformation;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return bankAccountInformation;
    }

    //probably won't use
    public long determineExistingBankAccount(BankAccountInformation bankAccountInformation) {
        String determine_existing_bank_account_query = "SELECT * " +
                "FROM bank_account_information " +
                "WHERE user_id = ?";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(determine_existing_bank_account_query);
            pst.setInt(1, bankAccountInformation.getUser_id());

            ResultSet rs = pst.executeQuery();

            if(!rs.next()) {
                return -1;
            } else {
                do {
                    long account_tracking_number = rs.getLong("account_tracking_number");
                    conn.close();
                    return account_tracking_number;
                } while(rs.next());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return -1;
    }

    // Open bank account binding GUI
    public boolean bindBankAccount(BankAccountInformation bankAccountInformation, Long tracking_number, String first_name, String middle_name, String last_name, String mobile_number) {


            String bank_account_query  = "SELECT account_tracking_number," +
                    "account_holder_first_name, " +
                    "account_holder_middle_name, " +
                    "account_holder_last_name, " +
                    "account_holder_mobile_number  " +
                    "FROM bank_account_information " +
                    "WHERE account_tracking_number = ? AND " +
                    "account_holder_first_name = ? AND " +
                    "account_holder_middle_name = ? AND " +
                    "account_holder_last_name = ? AND " +
                    "account_holder_mobile_number = ?";

            if (String.valueOf(tracking_number).isEmpty() ||
                    first_name.isEmpty() ||
                    middle_name.isEmpty() ||
                    last_name.isEmpty() ||
                    mobile_number.isEmpty()) {

                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            } else {
                try {
                    Connection conn = DBConnector.getConnection();
                    PreparedStatement pst = conn.prepareStatement(bank_account_query);
                    pst.setLong(1, tracking_number);
                    pst.setString(2, first_name);
                    pst.setString(3, middle_name);
                    pst.setString(4, last_name);
                    pst.setString(5, mobile_number);

                    ResultSet rs = pst.executeQuery();

                    if (!rs.next()) {
                        return false;
                    } else {
                        String bind_user_id = "UPDATE bank_account_information " +
                                "SET user_id = ?" +
                                "WHERE bank_account_information.account_tracking_number = ?";

                        PreparedStatement pst2 = conn.prepareStatement(bind_user_id);
                        pst2.setInt(1, bankAccountInformation.getUser_id());
                        pst2.setLong(2, tracking_number);
                        pst2.executeUpdate();

                        conn.close();
                        return true;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        return false;
    }


    public long transactionHistory(BankAccountInformation bankAccountInformation) {
        String determine_existing_bank_account_query = "SELECT * " +
                "FROM bank_account_information " +
                "WHERE user_id = ?";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement pst = conn.prepareStatement(determine_existing_bank_account_query);
            pst.setInt(1, bankAccountInformation.getUser_id());
            ResultSet rs = pst.executeQuery();

            if(!rs.next()) {
                return -1;
            } else {
                do {
                    conn.close();
                    return rs.getLong("account_tracking_number");
                } while(rs.next());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return -1;
    }

    public boolean sendMoney(BankAccountInformation bankAccountInformation, Long receiver_tracking_number, Double sendAmount) {
        String retrieve_receiver_account_details = "SELECT account_tracking_number," +
                "account_balance " +
                "FROM bank_account_information " +
                "WHERE account_tracking_number = ?";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(retrieve_receiver_account_details);
            ps.setLong(1, receiver_tracking_number);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                JOptionPane.showMessageDialog(null, "Account does not exist");

            } else {
                if (hasSufficientBalance(bankAccountInformation.getAccount_tracking_number(), sendAmount)) {
                    do {
                        Long account_tracking_number = rs.getLong("account_tracking_number");
                        Double receiver_account_balance = rs.getDouble("account_balance");

                        if (sendAmount <= 100  || String.valueOf(sendAmount).isEmpty()) {
                            JOptionPane.showMessageDialog(null, "You cannot send less than 100");

                        } else {
                            //Update Receiver Balance
                            String update_receiver_balance = "UPDATE bank_account_information " +
                                    "SET account_balance = account_balance + ? " +
                                    "WHERE account_tracking_number = ?;";

                            PreparedStatement ps_update = conn.prepareStatement(update_receiver_balance);
                            ps_update.setDouble(1, sendAmount);
                            ps_update.setLong(2, receiver_tracking_number);
                            ps_update.executeUpdate();

                            //Update Sender Balance
                            String update_sender_balance = "UPDATE bank_account_information " +
                                    "SET account_balance = account_balance - ? " +
                                    "WHERE account_tracking_number = ?;";

                            PreparedStatement ps_update2 = conn.prepareStatement(update_sender_balance);
                            ps_update2.setDouble(1, sendAmount);
                            ps_update2.setLong(2, bankAccountInformation.getAccount_tracking_number());
                            ps_update2.executeUpdate();

                            //Create Transaction Log
                            String transaction_log = "INSERT INTO bank_account_transactions (" +
                                    "transaction_type, " +
                                    "transaction_sender_account_num, " +
                                    "transaction_amount, " +
                                    "transaction_receiver_account_num, " +
                                    "transaction_date) " +
                                    "VALUES (?,?,?,?,?) ";

                            PreparedStatement create_transaction_log = conn.prepareStatement(transaction_log);
                            create_transaction_log.setString(1, "Transfer");
                            create_transaction_log.setLong(2, bankAccountInformation.getAccount_tracking_number());
                            create_transaction_log.setDouble(3, sendAmount);
                            create_transaction_log.setLong(4, account_tracking_number);
                            create_transaction_log.setString(5, ft.format(new Date()));
                            create_transaction_log.executeUpdate();

                            //Successful message
                            JOptionPane.showMessageDialog(null, "Successfully sent money");

                            conn.close();
                            return true;
                        }
                    } while(rs.next());
                    conn.close();
                } else {
                    return false;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return false;
    }

    private boolean hasSufficientBalance(Long current_tracking_number, Double sendAmount) {
        String check_balance_query = "SELECT account_balance " +
                "FROM bank_account_information " +
                "WHERE account_tracking_number = ?";

        try {
            Connection conn = DBConnector.getConnection();
            PreparedStatement check_balance_statement = conn.prepareStatement(check_balance_query);
            check_balance_statement.setLong(1, current_tracking_number);
            ResultSet rs = check_balance_statement.executeQuery();

            while(rs.next()) {
                Double account_balance = rs.getDouble("account_balance");
                if (account_balance > sendAmount) {
                    conn.close();
                    return true;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }

    public boolean depositMoney(BankAccountInformation bankAccountInformation, Long receiver_tracking_number, Double sendAmount) {
        String deposit_amount_query = "UPDATE bank_account_information " +
                "SET account_balance = account_balance + ? " +
                "WHERE account_tracking_number = ?;";
        if (sendAmount <= 100 || sendAmount.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid deposit amount");
        } else {
            try {
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(deposit_amount_query);
                ps.setDouble(1, sendAmount);
                ps.setLong(2, receiver_tracking_number);
                ps.executeUpdate();

                String deposit_transaction_log = "INSERT INTO bank_account_transactions (" +
                        "transaction_type, " +
                        "transaction_sender_account_num, " +
                        "transaction_amount, " +
                        "transaction_receiver_account_num, " +
                        "transaction_date) " +
                        "VALUES (?,?,?,?,?) ";

                PreparedStatement create_transaction_log = conn.prepareStatement(deposit_transaction_log);
                create_transaction_log.setString(1, "Deposit");
                create_transaction_log.setLong(2, bankAccountInformation.getAccount_tracking_number());
                create_transaction_log.setDouble(3, sendAmount);
                create_transaction_log.setLong(4, receiver_tracking_number);
                create_transaction_log.setString(5, ft.format(new Date()));
                create_transaction_log.executeUpdate();
                conn.close();
                return true;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        return false;
    }

    public Object[] transactionList(long tracking_number, int getColumnCount) {
        String transaction_history_query = "SELECT * " +
                "FROM bank_account_transactions " +
                "WHERE transaction_sender_account_num = ?;";
        try {

            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(transaction_history_query);
            ps.setLong(1, tracking_number);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Object[] row = new Object[getColumnCount];
                for (int i = 1; i <= getColumnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }

                return row;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
