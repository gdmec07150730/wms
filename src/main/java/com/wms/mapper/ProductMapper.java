package com.wms.mapper;

import com.wms.domain.Product;
import com.wms.query.QueryObject;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);

    int queryForCount(QueryObject qo);

    List<Product> queryForList(QueryObject qo);

}