package com.wms.service;

import com.wms.domain.Role;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;

import java.util.List;

public interface IRoleService {

    void deleteByPrimaryKey(Long id);

    void insert(Role record, Long[] ids, Long[] menuIds);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    PageResult query(QueryObject qo);

    void updateByPrimaryKey(Role record,Long[] ids,Long[] menuIds);

    void deleteRelation(Long id);

    void batchDelete(Long[] ids);
}
