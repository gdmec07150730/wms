package com.wms.service.impl;

import com.wms.domain.Department;
import com.wms.mapper.DepartmentMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public void deleteByPrimaryKey(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    public void insert(Department record) {
        departmentMapper.insert(record);
    }

    public Department selectByPrimaryKey(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public List<Department> selectAll() {
        return departmentMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = departmentMapper.queryForCount(qo);
        if (rows == 0 ){
            return PageResult.EMPTY_PAGE;
        }
        List<Department> list = departmentMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,list);
    }

    public void updateByPrimaryKey(Department record) {
        departmentMapper.updateByPrimaryKey(record);
    }

    public void batchDelete(Long[] ids) {
        departmentMapper.batchDelete(ids);
    }
}
