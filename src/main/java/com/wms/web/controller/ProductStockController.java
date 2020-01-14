package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.query.ProductStockQueryObject;
import com.wms.service.IBrandService;
import com.wms.service.IDepotService;
import com.wms.service.IProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("productStock")
public class ProductStockController extends BaseController {


    @Autowired
    private IProductStockService productStockService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private IDepotService depotService;

    @RequestMapping("query")
    @RequiredPermission("即时库存报表")
    public String query(Model model,@ModelAttribute("qo") ProductStockQueryObject qo){

        model.addAttribute("result",productStockService.query(qo));
        model.addAttribute("brands",brandService.selectAll());
        model.addAttribute("depots",depotService.selectAll());
        return "productstock/list";
    }

}
