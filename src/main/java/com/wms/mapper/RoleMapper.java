package com.wms.mapper;

import com.wms.domain.Role;
import com.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    int queryForCount(QueryObject qo);

    List<Role> queryForList(QueryObject qo);

    void batchDelete(Long[] ids);

    void insertRelation(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deleteRelation(Long id);

    void insertMenuRelation(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    void deleteMenuRelation(Long id);
}