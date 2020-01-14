package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Department;
import com.wms.query.QueryObject;
import com.wms.service.IDepartmentService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("department")
public class DepartmentController extends BaseController {

    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("query")
    @RequiredPermission("部门列表")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) {

        model.addAttribute("result", departmentService.query(qo));
        return "department/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑部门")
    public String input(Long id, Model model) {

        if (id != null) {
            model.addAttribute("department", departmentService.selectByPrimaryKey(id));
        }
        return "department/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除部门")
    public JsonResult delete(Long id) {
        if (id != null) {
            departmentService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加部门")
    public JsonResult saveOrUpdate(Department department) throws Exception {

        if (department.getId() != null) {
            departmentService.updateByPrimaryKey(department);
        } else {
            departmentService.insert(department);
        }

        return success();
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    public JsonResult batchDelete(@RequestParam("ids[]") Long[] ids) {
        try {
            if (ids != null) {
                departmentService.batchDelete(ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return failed();
        }
        return success();
    }

}
