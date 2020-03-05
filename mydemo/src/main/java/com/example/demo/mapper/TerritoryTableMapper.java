package com.example.demo.mapper;

import com.example.demo.entity.TerritoryTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TerritoryTableMapper {
    TerritoryTable searchByID(int territoryID);
}
