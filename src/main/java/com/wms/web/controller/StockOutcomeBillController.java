package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.StockOutcomeBill;
import com.wms.query.StockOutcomeBillQueryObject;
import com.wms.service.IClientService;
import com.wms.service.IDepotService;
import com.wms.service.IStockOutcomeBillService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockOutcomeBill")
public class StockOutcomeBillController extends BaseController {

    @Autowired
    private IStockOutcomeBillService stockOutcomeBillService;

    @Autowired
    private IDepotService depotService;

    @Autowired
    private IClientService clientService;

    @RequestMapping("query")
    @RequiredPermission("销售出库单列表")
    public String query(Model model,@ModelAttribute("qo") StockOutcomeBillQueryObject qo) {

        model.addAttribute("depots",depotService.selectAll());
        model.addAttribute("clients",clientService.selectAll());
        model.addAttribute("result", stockOutcomeBillService.query(qo));
        return "stockoutcomebill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑销售出库单")
    public String input(Long id, Model model) {

        if (id != null) {
            StockOutcomeBill stockOutcomeBill = stockOutcomeBillService.selectByPrimaryKey(id);
            model.addAttribute("stockOutcomeBill", stockOutcomeBill);
        }
        model.addAttribute("depots",depotService.selectAll());
        model.addAttribute("clients",clientService.selectAll());
        return "stockoutcomebill/input";
    }
    @RequestMapping("view")
    @RequiredPermission("查看销售出库单")
    public String view(Long id, Model model) {

        if (id != null) {
            model.addAttribute("stockOutcomeBill", stockOutcomeBillService.selectByPrimaryKey(id));
        }
        return "stockoutcomebill/view";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除销售出库单")
    public JsonResult delete(Long id) {
        if (id != null) {
            stockOutcomeBillService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加销售出库单")
    public JsonResult saveOrUpdate(StockOutcomeBill stockOutcomeBill) throws Exception {

        if (stockOutcomeBill.getId() != null) {
            stockOutcomeBillService.updateByPrimaryKey(stockOutcomeBill);
        } else {
            stockOutcomeBillService.insert(stockOutcomeBill);
        }

        return success();
    }

    @RequestMapping("audit")
    @ResponseBody
    @RequiredPermission("审核销售出库单")
    public JsonResult audit(Long id) {
        if (id != null) {
            try {
                stockOutcomeBillService.audit(id);
            } catch (Exception e) {
                e.printStackTrace();
                return failed(e.getMessage());
            }
        }
        return success();
    }

}
