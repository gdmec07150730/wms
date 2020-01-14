package com.wms.mapper;

import com.wms.domain.StockIncomeBill;
import com.wms.query.QueryObject;

import java.util.List;

public interface StockIncomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBill record);

    StockIncomeBill selectByPrimaryKey(Long id);

    List<StockIncomeBill> selectAll();

    int updateByPrimaryKey(StockIncomeBill record);

    int queryForCount(QueryObject qo);

    List<StockIncomeBill> queryForList(QueryObject qo);

    void updateStatus(StockIncomeBill bill);
}