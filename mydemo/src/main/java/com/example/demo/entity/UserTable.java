package com.example.demo.entity;

public class UserTable {
    private int userID;
    private String password;
    private String userName;
    private int userType;
    private String gender;
    private String email;
    private String phone;

    public int getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserType() {
        return userType;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
