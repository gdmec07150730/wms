package com.wms.service.impl;

import com.wms.domain.Role;
import com.wms.mapper.RoleMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    private ApplicationContext ctx;

    public void deleteByPrimaryKey(Long id) {
        roleMapper.deleteRelation(id);
        roleMapper.deleteMenuRelation(id);
        roleMapper.deleteByPrimaryKey(id);
    }

    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        int rows = roleMapper.queryForCount(qo);

        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<Role> datas = roleMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, datas);
    }

    public void updateByPrimaryKey(Role record, Long[] ids,Long[] menuIds) {
        roleMapper.deleteRelation(record.getId());
        roleMapper.deleteMenuRelation(record.getId());
        if (ids != null) {
            for (Long id : ids) {
                roleMapper.insertRelation(record.getId(),id);
            }

        }
        if (menuIds != null) {
            for (Long id : menuIds) {
                roleMapper.insertMenuRelation(record.getId(),id);
            }

        }
        roleMapper.updateByPrimaryKey(record);
    }

    public void deleteRelation(Long id) {
        roleMapper.deleteRelation(id);
    }

    public void batchDelete(Long[] ids) {
        roleMapper.batchDelete(ids);
    }

    public void insert(Role record, Long[] ids,Long[] menuIds) {
        roleMapper.insert(record);
        if (ids != null) {
            for (Long id : ids) {
                roleMapper.insertRelation(record.getId(),id);
            }

        }
        if (menuIds != null) {
            for (Long id : menuIds) {
                roleMapper.insertMenuRelation(record.getId(),id);
            }

        }
    }

    public Role selectByPrimaryKey(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }


}
