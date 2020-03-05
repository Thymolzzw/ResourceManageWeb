package com.example.demo.user.service;

import com.example.demo.entity.*;

import java.util.List;


public interface UserService {
    int addResource(ResourceTable resourceTable);
    int addBelong(Belong belong);
    int addRelation(Relation relation);
    int addDiscuss(Discuss discuss);
    int addUser(UserTable userTable);
    int addDownLoad(DownLoad downLoad);
    ResourceTable searchResourceByName(String resourceName);
    List<ResourceTable> searchResourceByUserID(int userID);
    List<ResourceTable> searchResourceByType(int resourceType);
    List<ResourceTable> resourceList(int pagenum,int pagesize);
    ResourceTable searchResourceByID(int resourceID);
    List<Belong> searchBelongByResourceID(int resourceID);
    List<Belong> searchBelongByTerritoryID(int territoryID);
    List<Discuss> searchDiscussByResourceID(int resourceID);
    List<Relation> searchRelationByOne(int resourceID);
    List<Relation> searchRelationByTwo(int resourceID);
    Relation searchRelationByOneAndTwo(Relation relation);
    UserTable searchUserByID(int userID);
    UserTable searchUserByName(String userName);
    TerritoryTable searchTerritoryByID(int territoryID);
    List<DownLoad> searchDownLoadByResourceID(int resourceID);
    Belong searchBelongByResourceIDAndTerritoryID(int resourceID,int territoryID);
    int updateResource(ResourceTable resourceTable);
    int deleteBelongByResourceID(int resourceID);
    int deleteRelationByResourceID(int resourceID);
    int deleteResourceByID(int resourceID);
    int deleteDiscussByResourceID(int resourceID);
    int deleteDownLoadByResourceID(int resourceID);
    int deleteDiscussByID(int discussID);
}
