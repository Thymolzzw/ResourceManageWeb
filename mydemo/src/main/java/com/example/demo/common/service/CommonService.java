package com.example.demo.common.service;

import com.example.demo.entity.UserTable;

public interface CommonService {
    UserTable userLogin(UserTable userTable);
    int updateUser(UserTable userTable);
}
