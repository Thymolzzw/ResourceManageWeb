package com.example.demo.entity;

import java.sql.Struct;
import java.sql.Timestamp;


public class ResourceTable implements Comparable<ResourceTable> {
    private int resourceID;
    private String resourceName;
    private int resourceType;
    private String resourceLocation;
    private int visitVolume;
    private Timestamp uploadTime;
    private String introduction;
    private int userID;

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public int getVisitVolume() {
        return visitVolume;
    }

    public void setVisitVolume(int visitVolume) {
        this.visitVolume = visitVolume;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime =  uploadTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int compareTo(ResourceTable o) {
        return this.getResourceName().compareTo(o.getResourceName());
    }
}
