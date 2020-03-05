package com.example.demo.entity;

public class MyDiscuss {
    private int discussID;
    private String userName;
    private int resourceID;
    private String discussContent;

    public int getDiscussID() {
        return discussID;
    }

    public void setDiscussID(int discussID) {
        this.discussID = discussID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
