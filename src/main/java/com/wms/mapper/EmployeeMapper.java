package com.wms.mapper;

import com.wms.domain.Employee;
import com.wms.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);


    int queryForCount(EmployeeQueryObject qo);

    List<Employee> queryForList(EmployeeQueryObject qo);

    void batchDelete(Long[] ids);

    void insertRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    void deleteRelation(Long employeeId);

    Employee selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}