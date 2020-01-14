package com.wms.service;

import com.wms.domain.StockOutcomeBill;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IStockOutcomeBillService {

    void deleteByPrimaryKey(Long id);

    void insert(StockOutcomeBill record);

    StockOutcomeBill selectByPrimaryKey(Long id);

    List<StockOutcomeBill> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(StockOutcomeBill record);

    void audit(Long id);
}
