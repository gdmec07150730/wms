package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Client;
import com.wms.query.QueryObject;
import com.wms.service.IClientService;
import com.wms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("client")
public class ClientController extends BaseController {

    @Autowired
    private IClientService clientService;

    @RequestMapping("query")
    @RequiredPermission("客户列表")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) {

        model.addAttribute("result", clientService.query(qo));
        return "client/list";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑客户")
    public String input(Long id, Model model) {

        if (id != null) {
            model.addAttribute("client", clientService.selectByPrimaryKey(id));
        }

        return "client/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除客户")
    public JsonResult delete(Long id) {
        if (id != null) {
            clientService.deleteByPrimaryKey(id);
        }
        return success();
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加客户")
    public JsonResult saveOrUpdate(Client client) throws Exception {

        if (client.getId() != null) {
            clientService.updateByPrimaryKey(client);
        } else {
            clientService.insert(client);
        }

        return success();
    }


}
