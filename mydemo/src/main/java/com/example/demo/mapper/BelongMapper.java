package com.example.demo.mapper;

import com.example.demo.entity.Belong;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BelongMapper {
    int insertBelong(Belong belong);
    int deleteBelongByResourceID(int resourceID);
    List<Belong> searchBelongByResourceID(int resourceID);
    List<Belong> searchBelongByTerritoryID(int territoryID);
    Belong searchBelongByResourceIDAndTerritoryID(@Param(value = "resourceID") int resourceID, @Param(value = "territoryID") int territoryID);
}
