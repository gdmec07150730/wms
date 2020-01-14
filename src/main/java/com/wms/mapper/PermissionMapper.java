package com.wms.mapper;

import com.wms.domain.Permission;
import com.wms.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    List<Permission> selectAll();

    int queryForCount(QueryObject qo);

    List<Permission> queryForList(QueryObject qo);

    List<String> selectAllExpression();

    void batchDelete(Long[] ids);

    List<String> selectAllExpsByEmployeeId(Long id);
}