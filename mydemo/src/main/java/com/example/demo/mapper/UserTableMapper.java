package com.example.demo.mapper;

import com.example.demo.entity.UserTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserTableMapper {
    int insertUserTable(UserTable userTable);
    int deleteUserTableByUserName(String userName);
    int updateUserTableByUserID(UserTable userTable);
    List<UserTable> searchUserTableByUserType(int userType);
    UserTable searchUserTableByUserNameAndPasswordAndUserType(@Param("userName") String userName, @Param("password") String password,
                                                              @Param("userType") int userType);
    UserTable searchUserTableByUserID(int userID);
    UserTable searchUserTableByUserName(String userName);
}
