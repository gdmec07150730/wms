package com.wms.mapper;

import com.wms.domain.OrderBillItem;

public interface OrderBillItemMapper {

    int insert(OrderBillItem record);

    OrderBillItem selectItemsByOrderBillId(Long id);

    void deleteBillId(Long id);
}