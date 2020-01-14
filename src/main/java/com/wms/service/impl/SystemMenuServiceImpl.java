package com.wms.service.impl;

import com.wms.domain.Employee;
import com.wms.domain.SystemMenu;
import com.wms.mapper.SystemMenuMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.ISystemMenuService;
import com.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    @Autowired
    SystemMenuMapper systemMenuMapper;

    public void deleteByPrimaryKey(Long id) {
        systemMenuMapper.deleteByPrimaryKey(id);
    }

    public List<SystemMenu> selectAll() {
        return systemMenuMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        int rows = systemMenuMapper.queryForCount(qo);

        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<SystemMenu> datas = systemMenuMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, datas);
    }

    public void updateByPrimaryKey(SystemMenu record) {
        systemMenuMapper.updateByPrimaryKey(record);
    }

    public List<Map<String, Object>> queryParentMenus(SystemMenu parent) {

        List<Map<String, Object>> list = new ArrayList<>();

        while (parent != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", parent.getId());
            map.put("name", parent.getName());
            list.add(map);

            SystemMenu p = parent.getParent();
            System.out.println(p);
            if (p != null) {
                parent = systemMenuMapper.selectByPrimaryKey(p.getId());
            } else {
                parent = null;
            }
        }
        Collections.reverse(list);
        return list;
    }

    public List<Map<String, Object>> loadMenusByParentSn(String parentSn) {
        Employee user = UserContext.getCurrentUser();
        if (user.getAdmin()) {
            return systemMenuMapper.loadMenusByParentSn(parentSn);
        }else{
            return systemMenuMapper.loadMenusByParentSnAndEmpId(parentSn,user.getId());
        }

    }


    public void insert(SystemMenu record) {
        systemMenuMapper.insert(record);

    }

    public SystemMenu selectByPrimaryKey(Long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }


}
