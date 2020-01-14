package com.wms.service;

import com.wms.domain.Depot;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IDepotService {

    void deleteByPrimaryKey(Long id);

    void insert(Depot record);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(Depot record);

}
