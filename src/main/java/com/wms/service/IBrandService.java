package com.wms.service;

import com.wms.domain.Brand;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IBrandService {

    void deleteByPrimaryKey(Long id);

    void insert(Brand record);

    Brand selectByPrimaryKey(Long id);

    List<Brand> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(Brand record);

}
