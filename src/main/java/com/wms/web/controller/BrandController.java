package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Brand;
import com.wms.query.QueryObject;
import com.wms.service.IBrandService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("brand")
public class BrandController extends BaseController {

    @Autowired
    private IBrandService brandService;

    @RequestMapping("query")
    @RequiredPermission("品牌列表")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) {

        model.addAttribute("result", brandService.query(qo));
        return "brand/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑品牌")
    public String input(Long id, Model model) {

        if (id != null) {
            model.addAttribute("brand", brandService.selectByPrimaryKey(id));
        }
        return "brand/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除品牌")
    public JsonResult delete(Long id) {
        if (id != null) {
            brandService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加品牌")
    public JsonResult saveOrUpdate(Brand brand) throws Exception {

        if (brand.getId() != null) {
            brandService.updateByPrimaryKey(brand);
        } else {
            brandService.insert(brand);
        }

        return success();
    }


}
