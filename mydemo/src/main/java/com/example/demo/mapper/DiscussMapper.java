package com.example.demo.mapper;

import com.example.demo.entity.Discuss;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiscussMapper {
    int insertDiscuss(Discuss discuss);
    List<Discuss> searchDiscussByResourceID(int resourceID);
    int deleteDiscussByResourceID(int resourceID);
    int deleteDiscussByUserID(int userID);
    int deleteDiscussByID(int discussID);
}
