package com.wms.mapper;

import com.wms.domain.Department;
import com.wms.query.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int queryForCount(QueryObject qo);

    List<Department> queryForList(QueryObject qo);

    int updateByPrimaryKey(Department record);

    void batchDelete(Long[] ids);
}