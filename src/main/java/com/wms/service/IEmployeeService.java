package com.wms.service;

import com.wms.domain.Employee;
import com.wms.page.PageResult;
import com.wms.query.EmployeeQueryObject;

import java.util.List;

public interface IEmployeeService {

    int deleteByPrimaryKey(Long id);

    int insert(Employee record, Long[] ids);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record,Long[] ids);

    PageResult query(EmployeeQueryObject qo);

    void batchDelete(Long[] ids);
    void deleteRelation(Long id);

    void login(String username, String password);
}
