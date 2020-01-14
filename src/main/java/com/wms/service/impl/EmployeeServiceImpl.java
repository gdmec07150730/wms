package com.wms.service.impl;

import com.wms.domain.Employee;
import com.wms.mapper.EmployeeMapper;
import com.wms.mapper.PermissionMapper;
import com.wms.page.PageResult;
import com.wms.query.EmployeeQueryObject;
import com.wms.service.IEmployeeService;
import com.wms.util.MD5;
import com.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public int deleteByPrimaryKey(Long id) {
        employeeMapper.deleteRelation(id);
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public int insert(Employee record, Long[] ids) {

        int i = employeeMapper.insert(record);
        if (ids != null) {
            for (Long id : ids) {
                employeeMapper.insertRelation(record.getId(),id);
            }
        }
        return i;
    }

    public Employee selectByPrimaryKey(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }

    public int updateByPrimaryKey(Employee record,Long[] ids) {

        employeeMapper.deleteRelation(record.getId());

        int i = employeeMapper.updateByPrimaryKey(record);
        if (ids != null) {
            for (Long id : ids) {
                employeeMapper.insertRelation(record.getId(),id);
            }
        }
        return i ;
    }

    public PageResult query(EmployeeQueryObject qo) {

        int rows = employeeMapper.queryForCount(qo);
        if (rows ==0 ){
            return PageResult.EMPTY_PAGE;
        }

        List<Employee> datas = employeeMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,datas);
    }

    public void batchDelete(Long[] ids) {
        employeeMapper.batchDelete(ids);
    }

    public void deleteRelation(Long id) {
        employeeMapper.deleteRelation(id);
    }

    public void login(String username, String password) {

        Employee emp = employeeMapper.selectByUsernameAndPassword(username, MD5.encode(password));

        if (emp == null){
            throw new RuntimeException("用户名或密码错误");
        }

        UserContext.setCurrentUser(emp);
        System.out.println(emp.getAdmin());
        if(!emp.getAdmin()){
            UserContext.setCurrentPerms(permissionMapper.selectAllExpsByEmployeeId(emp.getId()));
        }
    }
}
