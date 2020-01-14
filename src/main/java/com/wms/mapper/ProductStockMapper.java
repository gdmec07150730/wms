package com.wms.mapper;

import com.wms.domain.ProductStock;
import com.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {

    int insert(ProductStock record);

    int updateByPrimaryKey(ProductStock record);

    ProductStock selectByDeptIdAndProductId(@Param("deptId") Long deptId, @Param("productId") Long productId);

    int queryForCount(QueryObject qo);

    List<ProductStock> queryForList(QueryObject qo);

}