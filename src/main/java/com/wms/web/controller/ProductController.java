package com.wms.web.controller;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Brand;
import com.wms.domain.Product;
import com.wms.query.ProductQueryObject;
import com.wms.service.IBrandService;
import com.wms.service.IProductService;
import com.wms.util.JsonResult;
import com.wms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("product")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    ServletContext ctx;

    @RequestMapping("query")
    @RequiredPermission("货品列表")
    public String query(Model model, @ModelAttribute("qo") ProductQueryObject qo) {

        model.addAttribute("brands",brandService.selectAll());
        model.addAttribute("result",productService.query(qo));
        return "product/list";
    }
    @RequestMapping("selectProductList")
    @RequiredPermission("选择货品列表")
    public String selectProductList(Model model, @ModelAttribute("qo") ProductQueryObject qo) {

        model.addAttribute("brands",brandService.selectAll());
        model.addAttribute("result", productService.query(qo));
        return "product/selectProductList";
    }

    @RequestMapping("input")
    @RequiredPermission("编辑货品")
    public String input(Long id, Model model) {

        if (id != null) {
            model.addAttribute("product", productService.selectByPrimaryKey(id));
        }

        model.addAttribute("brands",brandService.selectAll());
        return "product/input";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("删除货品")
    public JsonResult delete(Long id,String imagePath) {
        if (id != null) {
            productService.deleteByPrimaryKey(id);
            if(imagePath != null){
                UploadUtil.deleteFile(ctx,imagePath);
            }
        }
        return success();
    }



    @RequestMapping("saveOrUpdate")
    @ResponseBody
    @RequiredPermission("更新/添加货品")
    public JsonResult saveOrUpdate(Product product, MultipartFile pic) throws Exception {

        if (product.getBrandId() != null) {
            Brand brand = brandService.selectByPrimaryKey(product.getBrandId());
            product.setBrandName(brand.getName());
        }

        if (pic != null) {

            //如果原来有图片,先删除原来的图片
            if (product.getImagePath() != null) {
                UploadUtil.deleteFile(ctx,product.getImagePath());
            }

            String filePath = UploadUtil.upload(pic, ctx.getRealPath("/upload"));
            product.setImagePath(filePath);
        }

        if (product.getId() != null) {
            productService.updateByPrimaryKey(product);
        } else {
            productService.insert(product);
        }

        return success();
    }


}
