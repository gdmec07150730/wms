package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.SystemMenu;
import com.wms.query.SystemMenuQueryObject;
import com.wms.service.ISystemMenuService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemMenu")
public class SystemMenuController extends BaseController {

    @Autowired
    private ISystemMenuService systemMenuService;


    @RequestMapping("query")
    @RequiredPermission("菜单列表")
    public String query(Model model, @ModelAttribute("qo") SystemMenuQueryObject qo) {

        if (qo.getParentId() != null) {
            SystemMenu parent = systemMenuService.selectByPrimaryKey(qo.getParentId());
            //获取每个parent对象
            model.addAttribute("menus",systemMenuService.queryParentMenus(parent));
        }

        model.addAttribute("result", systemMenuService.query(qo));

        return "systemMenu/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑菜单")
    public String input(Long id,Long parentId, Model model) {

        if (parentId == null) {
           model.addAttribute("parentName","根目录");
        }else{
            SystemMenu menu = systemMenuService.selectByPrimaryKey(parentId);
            model.addAttribute("parentName",menu.getName());
            model.addAttribute("parentId",menu.getId());
        }

        if (id != null) {
            SystemMenu systemMenu = systemMenuService.selectByPrimaryKey(id);
            model.addAttribute("systemMenu", systemMenu);
        }
        return "systemMenu/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除菜单")
    public JsonResult delete(Long id) {

        if (id != null) {
            systemMenuService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加菜单")
    public JsonResult saveOrUpdate(SystemMenu systemMenu, Long parentId) throws Exception {


        if(parentId != null){
            SystemMenu parent = new SystemMenu();
            parent.setId(parentId);
            systemMenu.setParent(parent);
        }

        if (systemMenu.getId() != null) {
            systemMenuService.updateByPrimaryKey(systemMenu);
        } else {
            systemMenuService.insert(systemMenu);
        }
        return success();
    }

    @RequestMapping("loadMenusByParentSn")
    @ResponseBody
    public List<Map<String,Object>> loadMenusByParentSn(String parentSn){
        return systemMenuService.loadMenusByParentSn(parentSn);
    }

}
