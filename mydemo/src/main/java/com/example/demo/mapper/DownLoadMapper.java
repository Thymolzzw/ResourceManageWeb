package com.example.demo.mapper;

import com.example.demo.entity.DownLoad;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DownLoadMapper {
    int insertDownLoad(DownLoad downLoad);
    List<DownLoad> searchDownLoadByUserID(int userID);
    List<DownLoad> searchDownLoadByResourceID(int resourceID);
    int deleteDownLoadByUserID(int userID);
    int deleteDownLoadByResourceID(int resourceID);
}
