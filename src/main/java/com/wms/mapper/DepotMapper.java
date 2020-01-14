package com.wms.mapper;

import com.wms.domain.Depot;
import com.wms.query.QueryObject;

import java.util.List;

public interface DepotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depot record);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    int updateByPrimaryKey(Depot record);
    int queryForCount(QueryObject qo);

    List<Depot> queryForList(QueryObject qo);
}