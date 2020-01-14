package com.wms.mapper;

import com.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ChartMapper {

    List<Map<String,Object>> queryOrderChart(QueryObject qo);

    List<Map<String,Object>> querySaleChart(QueryObject qo);
}
