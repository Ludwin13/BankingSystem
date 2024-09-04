package org.example.Model;

public class User_Credentials {
    private int user_id;
    private String user_email;
    private String user_name;
    private String user_password;
    private String date_created;

    public User_Credentials() {
    }

    public User_Credentials(int user_id, String user_email,
                            String user_name, String user_password,
                            String date_created) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_password = user_password;
        this.date_created = date_created;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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




}
