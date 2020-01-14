package com.wms.service.impl;

import com.wms.domain.ProductStock;
import com.wms.mapper.ProductStockMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStockServiceImpl implements IProductStockService {

    @Autowired
    private ProductStockMapper productStockMapper;

    public PageResult query(QueryObject qo) {

        int rows = productStockMapper.queryForCount(qo);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<ProductStock> list = productStockMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,list);
    }
}
