package com.example.demo.mapper;

import com.example.demo.entity.ResourceTable;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;


@Mapper
public interface ResourceTableMapper {
    int insertResource(ResourceTable resourceTable);
    int deleteResourceByID(int resourceID);
    ResourceTable searchResourceByID(int resourceID);
    ResourceTable searchResourceByName(String resourceName);
    List<ResourceTable> searchResourceByType(int resourceType);
    List<ResourceTable> searchResourceByUserID(int userID);
    List<ResourceTable> resourceList(@Param("pagenum") int pagenum, @Param("pagesize") int pagesize);
    int updateResource(ResourceTable resourceTable);

}
