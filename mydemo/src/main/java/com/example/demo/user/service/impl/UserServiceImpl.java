package com.example.demo.user.service.impl;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ResourceTableMapper resourceTableMapper;
    @Autowired
    BelongMapper belongMapper;
    @Autowired
    RelationMapper relationMapper;
    @Autowired
    DiscussMapper discussMapper;
    @Autowired
    UserTableMapper userTableMapper;
    @Autowired
    TerritoryTableMapper territoryTableMapper;
    @Autowired
    DownLoadMapper downLoadMapper;


    @Override
    @Transactional
    public int addResource(ResourceTable resourceTable) {
        return resourceTableMapper.insertResource(resourceTable);
    }

    @Override
    @Transactional
    public int addBelong(Belong belong) {
        return belongMapper.insertBelong(belong);
    }

    @Override
    public int addRelation(Relation relation) {
        return relationMapper.insertRelation(relation);
    }

    @Override
    public int addDiscuss(Discuss discuss) {
        return discussMapper.insertDiscuss(discuss);
    }

    @Override
    public int addUser(UserTable userTable) {
        return userTableMapper.insertUserTable(userTable);
    }

    @Override
    public int addDownLoad(DownLoad downLoad) {
        return downLoadMapper.insertDownLoad(downLoad);
    }

    @Override
    @Transactional
    public ResourceTable searchResourceByName(String resourceName) {
        return resourceTableMapper.searchResourceByName(resourceName);
    }

    @Override
    public List<ResourceTable> searchResourceByUserID(int userID) {
        return resourceTableMapper.searchResourceByUserID(userID);
    }

    @Override
    public List<ResourceTable> searchResourceByType(int resourceType) {
        return resourceTableMapper.searchResourceByType(resourceType);
    }

    @Override
    public List<ResourceTable> resourceList(int pagenum, int pagesize) {
        return resourceTableMapper.resourceList(pagenum,pagesize);
    }

    @Override
    public ResourceTable searchResourceByID(int resourceID) {
        return resourceTableMapper.searchResourceByID(resourceID);
    }

    @Override
    public List<Belong> searchBelongByResourceID(int resourceID) {
        return belongMapper.searchBelongByResourceID(resourceID);
    }

    @Override
    public List<Belong> searchBelongByTerritoryID(int territoryID) {
        return belongMapper.searchBelongByTerritoryID(territoryID);
    }

    @Override
    public List<Discuss> searchDiscussByResourceID(int resourceID) {
        return discussMapper.searchDiscussByResourceID(resourceID);
    }

    @Override
    public List<Relation> searchRelationByOne(int resourceID) {
        Relation re=new Relation();
        re.setResourceIDOne(resourceID);
        return relationMapper.searchRelationByOne(re);
    }

    @Override
    public List<Relation> searchRelationByTwo(int resourceID) {
        Relation re=new Relation();
        re.setResourceIDTwo(resourceID);
        return relationMapper.searchRelationByTwo(re);
    }

    @Override
    public Relation searchRelationByOneAndTwo(Relation relation) {
        return relationMapper.searchRelationByOneAndTwo(relation);
    }

    @Override
    public UserTable searchUserByID(int userID) {
        return userTableMapper.searchUserTableByUserID(userID);
    }

    @Override
    public UserTable searchUserByName(String userName) {
        return userTableMapper.searchUserTableByUserName(userName);
    }

    @Override
    public TerritoryTable searchTerritoryByID(int territoryID) {
        return territoryTableMapper.searchByID(territoryID);
    }

    @Override
    public List<DownLoad> searchDownLoadByResourceID(int resourceID) {
        return downLoadMapper.searchDownLoadByResourceID(resourceID);
    }

    @Override
    public Belong searchBelongByResourceIDAndTerritoryID(int resourceID, int territoryID) {
        return belongMapper.searchBelongByResourceIDAndTerritoryID(resourceID,territoryID);
    }

    @Override
    public int updateResource(ResourceTable resourceTable) {
        return resourceTableMapper.updateResource(resourceTable);
    }

    @Override
    public int deleteBelongByResourceID(int resourceID) {
        return belongMapper.deleteBelongByResourceID(resourceID);
    }

    @Override
    public int deleteRelationByResourceID(int resourceID) {
        return relationMapper.deleteRelationByResourceID(resourceID);
    }

    @Override
    public int deleteResourceByID(int resourceID) {
        return resourceTableMapper.deleteResourceByID(resourceID);
    }

    @Override
    public int deleteDiscussByResourceID(int resourceID) {
        return discussMapper.deleteDiscussByResourceID(resourceID);
    }

    @Override
    public int deleteDownLoadByResourceID(int resourceID) {
        return downLoadMapper.deleteDownLoadByResourceID(resourceID);
    }

    @Override
    public int deleteDiscussByID(int discussID) {
        return discussMapper.deleteDiscussByID(discussID);
    }

}
