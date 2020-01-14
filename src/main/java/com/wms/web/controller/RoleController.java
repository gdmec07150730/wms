package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Role;
import com.wms.query.QueryObject;
import com.wms.service.IPermissionService;
import com.wms.service.IRoleService;
import com.wms.service.ISystemMenuService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private ISystemMenuService systemMenuService;

    @RequestMapping("query")
    @RequiredPermission("角色列表")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) {

        model.addAttribute("result", roleService.query(qo));
        return "role/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑角色")
    public String input(Long id, Model model) {

        if (id != null) {
            Role role = roleService.selectByPrimaryKey(id);
            model.addAttribute("role", role);
        }
        model.addAttribute("permissions",permissionService.selectAll());
        model.addAttribute("menus",systemMenuService.selectAll());
        return "role/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除角色")
    public JsonResult delete(Long id) {

        if (id != null) {
            roleService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加角色")
    public JsonResult saveOrUpdate(Role role,Long[] ids,Long[] menuIds) throws Exception {

        if (role.getId() != null) {
            roleService.updateByPrimaryKey(role,ids,menuIds);
        } else {
            roleService.insert(role,ids,menuIds);
        }

        return success();
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    public JsonResult batchDelete(@RequestParam("ids[]") Long[] ids) {
        try {
            if (ids != null) {
                roleService.batchDelete(ids);
                for (Long id : ids) {
                    roleService.deleteRelation(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return failed();
        }
        return success();
    }

}
