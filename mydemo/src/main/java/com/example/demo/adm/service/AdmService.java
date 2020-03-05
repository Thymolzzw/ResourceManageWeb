package com.example.demo.adm.service;

import com.example.demo.entity.UserTable;

import java.util.List;

public interface AdmService {
    List<UserTable> searchRegisterUser();
    int deleteDownLoadByUserID(int userID);
    int deleteDiscussByUserID(int userID);
    int deleteUserByName(String userName);
}
