package com.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class ProductQueryObject extends QueryObject {

    private String keyword;
    private Long brandId;

}
