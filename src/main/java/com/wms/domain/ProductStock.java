package com.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter@Getter
public class ProductStock extends BaseDomain {

    private BigDecimal price;
    private BigDecimal storeNumber;
    private BigDecimal amount;

    private Depot depot;
    private Product product;

}
