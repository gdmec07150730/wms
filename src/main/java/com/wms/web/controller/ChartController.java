package com.wms.web.controller;

import com.wms.query.OrderChartQueryObject;
import com.wms.query.SaleChartQueryObject;
import com.wms.service.IBrandService;
import com.wms.service.IChartService;
import com.wms.service.IClientService;
import com.wms.service.ISupplierService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private IChartService chartService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IBrandService brandService;

    @RequestMapping("order")
    public String orderChart(Model model, @ModelAttribute("qo") OrderChartQueryObject qo) {

        qo.setPageSize(20);
        model.addAttribute("suppliers", supplierService.selectAll());
        model.addAttribute("brands", brandService.selectAll());
        model.addAttribute("groupTypes", OrderChartQueryObject.GROUP_BY_TYPES);
        model.addAttribute("datas", chartService.queryOrderChart(qo));

        return "chart/order";

    }

    @RequestMapping("sale")
    public String saleChart(Model model, @ModelAttribute("qo") SaleChartQueryObject qo) {

        qo.setPageSize(20);
        model.addAttribute("clients", clientService.selectAll());
        model.addAttribute("brands", brandService.selectAll());
        model.addAttribute("groupTypes", SaleChartQueryObject.GROUP_BY_TYPES);
        model.addAttribute("datas", chartService.querySaleChart(qo));
        return "chart/sale";
    }

    @RequestMapping("saleChartByBar")
    public String chartByBar(Model model, @ModelAttribute("qo") SaleChartQueryObject qo) {

        List<Map<String, Object>> mapList = chartService.querySaleChart(qo);

        List<String> groupTypes = new ArrayList<>();

        List<BigDecimal> totalAmounts = new ArrayList<>();

        for (Map<String, Object> map : mapList) {
            groupTypes.add((String) map.get("groupByType"));
            totalAmounts.add((BigDecimal) map.get("totalAmount"));
        }
        model.addAttribute("groupTypes", JSON.toJSONString(groupTypes));
        model.addAttribute("totalAmounts", JSON.toJSONString(totalAmounts));
        model.addAttribute("groupType", SaleChartQueryObject.GROUP_BY_TYPES.get(qo.getGroupType()));
        return "chart/chartByBar";
    }

    @RequestMapping("saleChartByPie")
    public String chartByPie(Model model, @ModelAttribute("qo") SaleChartQueryObject qo) {

        List<Map<String, Object>> mapList = chartService.querySaleChart(qo);

        List<String> groupTypes = new ArrayList<>();

        List<Map<String, Object>> datas = new ArrayList<>();
        BigDecimal maxAmount = BigDecimal.ZERO;
        for (Map<String, Object> map : mapList) {
            String groupType = (String) map.get("groupByType");
            BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");

            groupTypes.add(groupType);

            Map<String, Object> json = new HashMap<>();
            if (maxAmount.compareTo(totalAmount) < 0) {
                maxAmount = totalAmount;
            }
            json.put("name", groupType);
            json.put("value", totalAmount);

            datas.add(json);
        }
        model.addAttribute("maxAmount", maxAmount);
        model.addAttribute("groupTypes", JSON.toJSONString(groupTypes));
        model.addAttribute("datas", JSON.toJSONString(datas));
        model.addAttribute("groupType", SaleChartQueryObject.GROUP_BY_TYPES.get(qo.getGroupType()));
        return "chart/chartByPie";
    }


}


