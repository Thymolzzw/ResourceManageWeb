package com.example.demo.adm.service.impl;

import com.example.demo.adm.service.AdmService;
import com.example.demo.entity.UserTable;
import com.example.demo.mapper.DiscussMapper;
import com.example.demo.mapper.DownLoadMapper;
import com.example.demo.mapper.UserTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdmServiceimpl implements AdmService {
    @Autowired
    UserTableMapper userTableMapper;
    @Autowired
    DownLoadMapper downLoadMapper;
    @Autowired
    DiscussMapper discussMapper;

    @Override
    public List<UserTable> searchRegisterUser() {
        return userTableMapper.searchUserTableByUserType(1);
    }

    @Override
    public int deleteDownLoadByUserID(int userID) {
        return downLoadMapper.deleteDownLoadByUserID(userID);
    }

    @Override
    public int deleteDiscussByUserID(int userID) {
        return discussMapper.deleteDiscussByUserID(userID);
    }

    @Override
    public int deleteUserByName(String userName) {
        return userTableMapper.deleteUserTableByUserName(userName);
    }
}
