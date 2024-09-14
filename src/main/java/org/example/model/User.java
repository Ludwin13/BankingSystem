package org.example.model;

public class User {
    private int user_id;
    private String user_name;
    private String user_first_name;
    private String user_middle_name;
    private String user_last_name;
    private String user_email;
    private String user_password;
    private String date_created;

    public User() {
    }

    public User(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public User(String user_name, String user_first_name, String user_middle_name, String user_last_name, String user_email, String user_password) {
        this.user_name = user_name;
        this.user_first_name = user_first_name;
        this.user_middle_name = user_middle_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_password = user_password;
    }

    public User(String user_name, String user_first_name, String user_middle_name, String user_last_name, String user_email, String user_password, String date_created) {
        this.user_name = user_name;
        this.user_first_name = user_first_name;
        this.user_middle_name = user_middle_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.date_created = date_created;
    }

    public User(int user_id, String user_name, String user_first_name, String user_middle_name, String user_last_name, String user_email, String user_password, String date_created) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_first_name = user_first_name;
        this.user_middle_name = user_middle_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.date_created = date_created;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_middle_name() {
        return user_middle_name;
    }

    public void setUser_middle_name(String user_middle_name) {
        this.user_middle_name = user_middle_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String return_full_name() {
        return user_first_name + " " + user_middle_name + " " + user_last_name;
    }
}
