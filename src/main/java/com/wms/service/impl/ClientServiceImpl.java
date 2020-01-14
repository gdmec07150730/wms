package com.wms.service.impl;

import com.wms.domain.Client;
import com.wms.mapper.ClientMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    public void deleteByPrimaryKey(Long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    public void insert(Client record) {
        clientMapper.insert(record);
    }

    public Client selectByPrimaryKey(Long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    public List<Client> selectAll() {
        return clientMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = clientMapper.queryForCount(qo);
        if (rows == 0 ){
            return PageResult.EMPTY_PAGE;
        }
        List<Client> list = clientMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,list);
    }

    public void updateByPrimaryKey(Client record) {
        clientMapper.updateByPrimaryKey(record);
    }
}
