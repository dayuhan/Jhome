package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.model.response.GetRoleResponse;
import com.account.modules.userAuthority.model.response.GetRoleSysMenuResponse;
import com.account.modules.userAuthority.model.response.GetSysMenuResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */

public interface SysMenuRoleMapper{
    List<GetSysMenuResponse> findMenuByUserId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

    List<GetSysMenuResponse> findAllMenu(@Param("tenantId") Long tenantId);

    IPage<GetRoleResponse> findRoleByUserId(IPage<GetRoleResponse> page, @Param("userId") Long userId, @Param("tenantId") Long tenantId);

    IPage<GetRoleResponse> findRoleByTenantId(IPage<GetRoleResponse> page, @Param("tenantId") Long tenantId);

    IPage<GetRoleResponse> findAllRole(IPage<GetRoleResponse> page);

    List<GetRoleSysMenuResponse> findLeafMenuByRoleId(@Param("roleId") Long roleId, @Param("tenantId") Long tenantId);

    /**
     * 递归查询子菜单id
     * @param id
     * @return
     */
    List<Long> findSubMenuId(@Param("id") Long id);

    List<GetRoleSysMenuResponse> findAllRoleLeafMenu(@Param("tenantId") Long tenantId);

    List<GetRoleResponse> findRoleByTenantId(@Param("tenantId") Long tenantId);
}
