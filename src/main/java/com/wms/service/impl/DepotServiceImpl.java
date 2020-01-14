package com.wms.service.impl;

import com.wms.domain.Depot;
import com.wms.mapper.DepotMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IDepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements IDepotService {

    @Autowired
    private DepotMapper depotMapper;

    public void deleteByPrimaryKey(Long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    public void insert(Depot record) {
        depotMapper.insert(record);
    }

    public Depot selectByPrimaryKey(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    public List<Depot> selectAll() {
        return depotMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = depotMapper.queryForCount(qo);
        if (rows == 0 ){
            return PageResult.EMPTY_PAGE;
        }
        List<Depot> list = depotMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,list);
    }

    public void updateByPrimaryKey(Depot record) {
        depotMapper.updateByPrimaryKey(record);
    }
}
