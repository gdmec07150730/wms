package com.wms.service.impl;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Permission;
import com.wms.mapper.PermissionMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IPermissionService;
import com.wms.util.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    private ApplicationContext ctx;

    public void deleteByPrimaryKey(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    public List<Permission> selectAll() {
        return permissionMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        int rows = permissionMapper.queryForCount(qo);

        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<Permission> datas = permissionMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,datas);
    }

    public void insert(Permission record) {
        permissionMapper.insert(record);
    }

    public List<String> selectAllExpression() {
        return permissionMapper.selectAllExpression();
    }

    public void reload() {
        Map<String, Object> beansMaps = ctx.getBeansWithAnnotation(Controller.class);

        List<String> exps = permissionMapper.selectAllExpression();

        for (Object o : beansMaps.values()) {
            Method[] ms = o.getClass().getMethods();
            for (Method m : ms) {
                if (m.isAnnotationPresent(RequiredPermission.class)) {
                    RequiredPermission anno = m.getAnnotation(RequiredPermission.class);
                    String exp = PermissionUtil.buildExpression(m);
                    if (!exps.contains(exp)) {
                        Permission p = new Permission();
                        p.setName(anno.value());
                        p.setExpression(exp);
                        permissionMapper.insert(p);
                    }
                }
            }

        }
    }

    public void batchDelete(Long[] ids) {
        permissionMapper.batchDelete(ids);
    }
}
