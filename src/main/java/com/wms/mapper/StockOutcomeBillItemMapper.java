package com.wms.mapper;

import com.wms.domain.StockOutcomeBillItem;

public interface StockOutcomeBillItemMapper {

    int insert(StockOutcomeBillItem record);

    StockOutcomeBillItem selectItemsByBillId(Long id);

    void deleteBillId(Long id);
}