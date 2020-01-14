package com.wms.service;

import com.wms.domain.StockIncomeBill;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IStockIncomeBillService {

    void deleteByPrimaryKey(Long id);

    void insert(StockIncomeBill record);

    StockIncomeBill selectByPrimaryKey(Long id);

    List<StockIncomeBill> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(StockIncomeBill record);

    void audit(Long id);
}
