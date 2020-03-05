package com.example.demo.entity;


import java.sql.Date;
import java.sql.Timestamp;

public class DownLoad {
    private int downLoadID;
    private int userID;
    private int resourceID;
    private Timestamp downLoadTime;

    public int getDownLoadID() {
        return downLoadID;
    }

    public void setDownLoadID(int downLoadID) {
        this.downLoadID = downLoadID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public Timestamp getDownLoadTime() {
        return downLoadTime;
    }

    public void setDownLoadTime(Timestamp downLoadTime) {
        this.downLoadTime = downLoadTime;
    }
}
