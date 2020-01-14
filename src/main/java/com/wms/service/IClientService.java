package com.wms.service;

import com.wms.domain.Client;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IClientService {

    void deleteByPrimaryKey(Long id);

    void insert(Client record);

    Client selectByPrimaryKey(Long id);

    List<Client> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(Client record);

}
