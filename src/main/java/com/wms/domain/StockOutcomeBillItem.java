package com.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter@Setter
public class StockOutcomeBillItem extends BaseDomain {
    private BigDecimal salePrice;
    private BigDecimal number;
    private BigDecimal amount;
    private String remark;
    private Long billId;

    private Product product;

}
