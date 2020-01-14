package com.wms.service.impl;

import com.wms.domain.Supplier;
import com.wms.mapper.SupplierMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    public void deleteByPrimaryKey(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    public void insert(Supplier record) {
        supplierMapper.insert(record);
    }

    public Supplier selectByPrimaryKey(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    public List<Supplier> selectAll() {
        return supplierMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = supplierMapper.queryForCount(qo);
        if (rows == 0 ){
            return PageResult.EMPTY_PAGE;
        }
        List<Supplier> list = supplierMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,list);
    }

    public void updateByPrimaryKey(Supplier record) {
        supplierMapper.updateByPrimaryKey(record);
    }
}
