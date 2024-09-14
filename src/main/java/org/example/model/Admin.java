package org.example.model;

public class Admin {
    private int admin_id;
    private String admin_user_name;
    private String admin_first_name;
    private String admin_middle_name;
    private String admin_last_name;
    private String admin_password;
    private String date_created;

    public Admin() {
    }

    public Admin(String admin_user_name, String admin_password) {
        this.admin_user_name = admin_user_name;
        this.admin_password = admin_password;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_user_name() {
        return admin_user_name;
    }

    public void setAdmin_user_name(String admin_user_name) {
        this.admin_user_name = admin_user_name;
    }

    public String getAdmin_first_name() {
        return admin_first_name;
    }

    public void setAdmin_first_name(String admin_first_name) {
        this.admin_first_name = admin_first_name;
    }

    public String getAdmin_middle_name() {
        return admin_middle_name;
    }

    public void setAdmin_middle_name(String admin_middle_name) {
        this.admin_middle_name = admin_middle_name;
    }

    public String getAdmin_last_name() {
        return admin_last_name;
    }

    public void setAdmin_last_name(String admin_last_name) {
        this.admin_last_name = admin_last_name;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
