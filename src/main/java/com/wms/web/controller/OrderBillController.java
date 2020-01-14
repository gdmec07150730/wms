package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.OrderBill;
import com.wms.query.OrderBillQueryObject;
import com.wms.service.IOrderBillService;
import com.wms.service.ISupplierService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("orderBill")
public class OrderBillController extends BaseController {

    @Autowired
    private IOrderBillService orderBillService;

    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("query")
    @RequiredPermission("采购订单列表")
    public String query(Model model,@ModelAttribute("qo") OrderBillQueryObject qo) {

        model.addAttribute("suppliers",supplierService.selectAll());
        model.addAttribute("result", orderBillService.query(qo));
        return "orderbill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑采购订单")
    public String input(Long id, Model model) {

        model.addAttribute("suppliers",supplierService.selectAll());
        if (id != null) {
            model.addAttribute("orderBill", orderBillService.selectByPrimaryKey(id));
        }
        return "orderbill/input";
    }
    @RequestMapping("view")
    @RequiredPermission("查看采购订单")
    public String view(Long id, Model model) {

        if (id != null) {
            model.addAttribute("orderBill", orderBillService.selectByPrimaryKey(id));
        }
        return "orderbill/view";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除采购订单")
    public JsonResult delete(Long id) {
        if (id != null) {
            orderBillService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加采购订单")
    public JsonResult saveOrUpdate(OrderBill orderBill) throws Exception {

        if (orderBill.getId() != null) {
            orderBillService.updateByPrimaryKey(orderBill);
        } else {
            orderBillService.insert(orderBill);
        }

        return success();
    }

    @RequestMapping("audit")
    @ResponseBody
    @RequiredPermission("审核采购订单")
    public JsonResult audit(Long id) {
        if (id != null) {
            orderBillService.audit(id);
        }
        return success();
    }

}
