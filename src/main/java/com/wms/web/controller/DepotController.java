package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Depot;
import com.wms.query.QueryObject;
import com.wms.service.IDepotService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("depot")
public class DepotController extends BaseController {

    @Autowired
    private IDepotService depotService;

    @RequestMapping("query")
    @RequiredPermission("仓库列表")
    public String query(Model model, @ModelAttribute("qo") QueryObject qo) {

        model.addAttribute("result", depotService.query(qo));
        return "depot/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑仓库")
    public String input(Long id, Model model) {

        if (id != null) {
            model.addAttribute("depot", depotService.selectByPrimaryKey(id));
        }

        return "depot/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除仓库")
    public JsonResult delete(Long id) {
        if (id != null) {
            depotService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加仓库")
    public JsonResult saveOrUpdate(Depot depot) throws Exception {

        if (depot.getId() != null) {
            depotService.updateByPrimaryKey(depot);
        } else {
            depotService.insert(depot);
        }

        return success();
    }


}
