package com.example.demo.common.service.impl;

import com.example.demo.common.service.CommonService;
import com.example.demo.entity.UserTable;
import com.example.demo.mapper.UserTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {
    @Autowired()
    UserTableMapper userTableMapper;

    @Override
    public UserTable userLogin(UserTable userTable) {
        UserTable user=userTableMapper.searchUserTableByUserNameAndPasswordAndUserType(userTable.getUserName(),userTable.getPassword(),userTable.getUserType());
        if(user==null){
            System.out.println("没有此用户！");

        }else{
            System.out.println("查找到此用户，用户ID为 "+user.getUserID());
            System.out.println("-------------用户名为 "+user.getUserName());
            System.out.println("-------------用户密码为 "+user.getPassword());
            System.out.println("-------------用户类型为 "+user.getUserType());
            System.out.println("-------------用户性别为 "+user.getGender());
            System.out.println("-------------用户邮箱为 "+user.getEmail());
            System.out.println("-------------用户手机号为 "+user.getPhone());
        }
        return user;
    }

    @Override
    @Transactional
    public int updateUser(UserTable userTable) {
        return userTableMapper.updateUserTableByUserID(userTable);
    }
}
