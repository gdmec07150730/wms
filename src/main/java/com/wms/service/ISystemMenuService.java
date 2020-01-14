package com.wms.service;

import com.wms.domain.SystemMenu;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ISystemMenuService {

    void deleteByPrimaryKey(Long id);

    void insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(SystemMenu record);

    List<Map<String,Object>> queryParentMenus(SystemMenu parent);

    List<Map<String,Object>> loadMenusByParentSn(String parentSn);
}
