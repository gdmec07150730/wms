package com.wms.service.impl;

import com.wms.domain.Product;
import com.wms.mapper.ProductMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    public void deleteByPrimaryKey(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    public void insert(Product record) {
        productMapper.insert(record);
    }

    public Product selectByPrimaryKey(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public List<Product> selectAll() {
        return productMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = productMapper.queryForCount(qo);
        if (rows == 0 ){
            return PageResult.EMPTY_PAGE;
        }
        List<Product> list = productMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,list);
    }

    public void updateByPrimaryKey(Product record) {
        productMapper.updateByPrimaryKey(record);
    }
}
