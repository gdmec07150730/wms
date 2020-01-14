package com.wms.service.impl;

import com.wms.domain.Brand;
import com.wms.mapper.BrandMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    public void deleteByPrimaryKey(Long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    public void insert(Brand record) {
        brandMapper.insert(record);
    }

    public Brand selectByPrimaryKey(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<Brand> selectAll() {
        return brandMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = brandMapper.queryForCount(qo);
        if (rows == 0 ){
            return PageResult.EMPTY_PAGE;
        }
        List<Brand> list = brandMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,list);
    }

    public void updateByPrimaryKey(Brand record) {
        brandMapper.updateByPrimaryKey(record);
    }
}
