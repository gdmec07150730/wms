package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Department;
import com.wms.domain.Employee;
import com.wms.page.PageResult;
import com.wms.query.EmployeeQueryObject;
import com.wms.service.IDepartmentService;
import com.wms.service.IEmployeeService;
import com.wms.service.IRoleService;
import com.wms.util.JsonData;
import com.wms.util.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController extends BaseController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("query")
    @RequiredPermission("员工列表")
    public String query(Model model, @ModelAttribute("qo") EmployeeQueryObject qo){
        model.addAttribute("result",employeeService.query(qo));
        return "employee/list";
    }

    @RequestMapping("list")
    @ResponseBody
    public JsonData list(@ModelAttribute("qo") EmployeeQueryObject qo){
        JsonData data = new JsonData();
        PageResult result = employeeService.query(qo);
        data.setTotal(result.getTotalCount());
        data.setRows(result.getDatas());

        return data;
    }

    @RequestMapping("dept")
    @ResponseBody
    public String dept(@ModelAttribute("qo") EmployeeQueryObject qo){
        List<Department> depts = departmentService.selectAll();
        String json = JSON.toJSONString(depts);
        return JSON.toJSONString(depts);
    }


    @RequestMapping("input")
    @RequiredPermission("编辑员工")
    public String input(Long id,Model model){
        model.addAttribute("depts",departmentService.selectAll());
        model.addAttribute("roles",roleService.selectAll());
        if (id != null) {
            model.addAttribute("employee",employeeService.selectByPrimaryKey(id));
        }

        return "employee/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除员工")
    public JsonResult delete(Long id){
        if (id != null) {
            employeeService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加员工")
    public JsonResult saveOrUpdate(Employee employee,Long[] ids){
        if (employee.getId() != null) {
            employeeService.updateByPrimaryKey(employee,ids);
        }else{
            employeeService.insert(employee,ids);
        }
        return success();
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    public JsonResult batchDelete(@RequestParam("ids[]") Long[] ids){
        try{
            if (ids != null) {
                employeeService.batchDelete(ids);
                for (Long id : ids) {
                    employeeService.deleteRelation(id);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return failed();
        }
        return success();
    }

}
