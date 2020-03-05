package com.example.demo.mapper;

import com.example.demo.entity.Relation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationMapper {
    int insertRelation(Relation relation);
    List<Relation> searchRelationByOne(Relation relation);
    List<Relation> searchRelationByTwo(Relation relation);
    Relation searchRelationByOneAndTwo(Relation relation);
    int deleteRelationByResourceID(int resourceID);
}
