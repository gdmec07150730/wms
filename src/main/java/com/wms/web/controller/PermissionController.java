package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.query.QueryObject;
import com.wms.service.IPermissionService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("query")
    @RequiredPermission("权限列表")
    public String query(Model model,@ModelAttribute("qo") QueryObject qo) {

        model.addAttribute("result", permissionService.query(qo));

        return "permission/list";
    }

    @RequiredPermission("删除权限")
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        if (id != null) {
            permissionService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("reload")
    @ResponseBody
    @RequiredPermission("加载权限")
    public JsonResult reload(){
        permissionService.reload();
        return success();
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    public JsonResult batchDelete(@RequestParam("ids[]") Long[] ids){
        try{
            if (ids != null) {
                permissionService.batchDelete(ids);
            }
        }catch (Exception e){
            e.printStackTrace();
            return failed();
        }
        return success();
    }
}
