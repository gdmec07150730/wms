package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Supplier;
import com.wms.query.QueryObject;
import com.wms.service.ISupplierService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("supplier")
public class SupplierController extends BaseController {

    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("query")
    @RequiredPermission("供应商列表")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) {

        model.addAttribute("result", supplierService.query(qo));
        return "supplier/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑供应商")
    public String input(Long id, Model model) {

        if (id != null) {
            model.addAttribute("supplier", supplierService.selectByPrimaryKey(id));
        }

        return "supplier/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除供应商")
    public JsonResult delete(Long id) {
        if (id != null) {
            supplierService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加供应商")
    public JsonResult saveOrUpdate(Supplier supplier) throws Exception {

        if (supplier.getId() != null) {
            supplierService.updateByPrimaryKey(supplier);
        } else {
            supplierService.insert(supplier);
        }

        return success();
    }


}
