package com.wms.service;

import com.wms.domain.Supplier;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface ISupplierService {

    void deleteByPrimaryKey(Long id);

    void insert(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    List<Supplier> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(Supplier record);

}
