package com.wms.service;

import com.wms.page.PageResult;
import com.wms.query.QueryObject;

public interface IProductStockService {

    PageResult query(QueryObject qo);

}
