package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.StockIncomeBill;
import com.wms.page.PageResult;
import com.wms.query.StockIncomeBillQueryObject;
import com.wms.service.IStockIncomeBillService;
import com.wms.service.IDepotService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockIncomeBill")
public class StockIncomeBillController extends BaseController {

    @Autowired
    private IStockIncomeBillService stockIncomeBillService;

    @Autowired
    private IDepotService depotService;

    @RequestMapping("query")
    @RequiredPermission("采购入库单列表")
    public String query(Model model,@ModelAttribute("qo") StockIncomeBillQueryObject qo) {

        PageResult result = stockIncomeBillService.query(qo);
        model.addAttribute("depots",depotService.selectAll());
        model.addAttribute("result", result);
        return "stockincomebill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑采购入库单")
    public String input(Long id, Model model) {

        if (id != null) {
            model.addAttribute("stockIncomeBill", stockIncomeBillService.selectByPrimaryKey(id));
        }
        model.addAttribute("depots",depotService.selectAll());
        return "stockincomebill/input";
    }
    @RequestMapping("view")
    @RequiredPermission("查看采购入库单")
    public String view(Long id, Model model) {

        if (id != null) {
            model.addAttribute("stockIncomeBill", stockIncomeBillService.selectByPrimaryKey(id));
        }
        return "stockincomebill/view";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除采购入库单")
    public JsonResult delete(Long id) {
        if (id != null) {
            stockIncomeBillService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加采购入库单")
    public JsonResult saveOrUpdate(StockIncomeBill stockIncomeBill) throws Exception {

        if (stockIncomeBill.getId() != null) {
            stockIncomeBillService.updateByPrimaryKey(stockIncomeBill);
        } else {
            stockIncomeBillService.insert(stockIncomeBill);
        }

        return success();
    }

    @RequestMapping("audit")
    @ResponseBody
    @RequiredPermission("审核采购入库单")
    public JsonResult audit(Long id) {
        if (id != null) {
            stockIncomeBillService.audit(id);
        }
        return success();
    }

}
