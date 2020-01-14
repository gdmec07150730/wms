package com.wms.service.impl;

import com.wms.mapper.ChartMapper;
import com.wms.query.QueryObject;
import com.wms.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ChartServiceImpl implements IChartService {

    @Autowired
    private ChartMapper chartMapper;

    public List<Map<String, Object>> queryOrderChart(QueryObject qo) {
        return chartMapper.queryOrderChart(qo);
    }

    public List<Map<String, Object>> querySaleChart(QueryObject qo) {
        return chartMapper.querySaleChart(qo);
    }
}
