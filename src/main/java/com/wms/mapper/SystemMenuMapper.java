package com.wms.mapper;

import com.wms.domain.SystemMenu;
import com.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll();

    int updateByPrimaryKey(SystemMenu record);

    int queryForCount(QueryObject qo);

    List<SystemMenu> queryForList(QueryObject qo);

    List<Map<String,Object>> loadMenusByParentSn(String parentSn);

    List<Map<String,Object>> loadMenusByParentSnAndEmpId(@Param("parentSn") String parentSn, @Param("empId") Long empId);
}