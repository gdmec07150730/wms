package com.wms.service;

import com.wms.domain.Permission;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IPermissionService {

    void deleteByPrimaryKey(Long id);

    List<Permission> selectAll();

    PageResult query(QueryObject qo);

    void insert(Permission record);

    void reload();

    void batchDelete(Long[] ids);
}
