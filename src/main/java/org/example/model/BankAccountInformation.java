package org.example.model;

import org.example.dao.BankAccountInformationDAO;

public class BankAccountInformation {
    private long account_tracking_number;
    private int user_id;
    private double account_balance;
    private String account_holder_first_name;
    private String account_holder_middle_name;
    private String account_holder_last_name;
    private String account_holder_mobile_number;
    private String account_holder_address;
    private String account_holder_valid_id;
    private String date_created;

    public BankAccountInformation() {
    }

    public BankAccountInformation(int user_id) {
        this.user_id = user_id;
    }

    public BankAccountInformation(long account_tracking_number, int user_id) {
        this.account_tracking_number = account_tracking_number;
        this.user_id = user_id;
    }

    public BankAccountInformation(long account_tracking_number, int user_id, double account_balance, String account_holder_first_name, String account_holder_middle_name, String account_holder_last_name, String account_holder_mobile_number) {
        this.account_tracking_number = account_tracking_number;
        this.user_id = user_id;
        this.account_balance = account_balance;
        this.account_holder_first_name = account_holder_first_name;
        this.account_holder_middle_name = account_holder_middle_name;
        this.account_holder_last_name = account_holder_last_name;
        this.account_holder_mobile_number = account_holder_mobile_number;
    }

    public long getAccount_tracking_number() {
        return account_tracking_number;
    }

    public void setAccount_tracking_number(long account_tracking_number) {
        this.account_tracking_number = account_tracking_number;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
    }

    public String getAccount_holder_first_name() {
        return account_holder_first_name;
    }

    public void setAccount_holder_first_name(String account_holder_first_name) {
        this.account_holder_first_name = account_holder_first_name;
    }

    public String getAccount_holder_middle_name() {
        return account_holder_middle_name;
    }

    public void setAccount_holder_middle_name(String account_holder_middle_name) {
        this.account_holder_middle_name = account_holder_middle_name;
    }

    public String getAccount_holder_last_name() {
        return account_holder_last_name;
    }

    public void setAccount_holder_last_name(String account_holder_last_name) {
        this.account_holder_last_name = account_holder_last_name;
    }

    public String getAccount_holder_mobile_number() {
        return account_holder_mobile_number;
    }

    public void setAccount_holder_mobile_number(String account_holder_mobile_number) {
        this.account_holder_mobile_number = account_holder_mobile_number;
    }

    public String getAccount_holder_address() {
        return account_holder_address;
    }

    public void setAccount_holder_address(String account_holder_address) {
        this.account_holder_address = account_holder_address;
    }

    public String getAccount_holder_valid_id() {
        return account_holder_valid_id;
    }

    public void setAccount_holder_valid_id(String account_holder_valid_id) {
        this.account_holder_valid_id = account_holder_valid_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return account_tracking_number +
                ":" + user_id +
                ":" + account_balance +
                ":" + account_holder_first_name +
                ":" + account_holder_middle_name +
                ":" + account_holder_last_name +
                ":" + account_holder_mobile_number +
                ":" + account_holder_address;

    }

    public String getFull_name() {
        return account_holder_first_name + " " + account_holder_middle_name + " " + account_holder_last_name;
    }
}
