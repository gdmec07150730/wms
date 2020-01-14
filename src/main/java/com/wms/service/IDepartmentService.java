package com.wms.service;

import com.wms.domain.Department;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IDepartmentService {

    void deleteByPrimaryKey(Long id);

    void insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(Department record);


    void batchDelete(Long[] ids);
}
