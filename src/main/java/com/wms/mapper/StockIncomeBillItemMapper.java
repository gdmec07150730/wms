package com.wms.mapper;

import com.wms.domain.StockIncomeBillItem;

public interface StockIncomeBillItemMapper {

    int insert(StockIncomeBillItem record);

    StockIncomeBillItem selectItemsByBillId(Long id);

    void deleteBillId(Long id);
}