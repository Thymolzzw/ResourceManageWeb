package com.example.demo.entity;

public class Discuss {
    private int discussID;
    private int userID;
    private int resourceID;
    private String discussContent;

    public int getDiscussID() {
        return discussID;
    }

    public void setDiscussID(int discussID) {
        this.discussID = discussID;
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

    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent;
    }
}
