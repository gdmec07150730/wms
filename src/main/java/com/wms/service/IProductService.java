package com.wms.service;

import com.wms.domain.Product;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IProductService {

    void deleteByPrimaryKey(Long id);

    void insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(Product record);

}
