package com.wms.service;

import com.wms.domain.OrderBill;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IOrderBillService {

    void deleteByPrimaryKey(Long id);

    void insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    List<OrderBill> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(OrderBill record);

    void audit(Long id);
}
