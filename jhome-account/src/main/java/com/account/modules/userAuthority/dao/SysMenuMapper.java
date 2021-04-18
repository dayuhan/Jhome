package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询下一级子菜单
     * @param menuId
     * @return
     */
    @Select(value="SELECT t.id FROM sys_menu t WHERE t.delete_flag = 0 AND t.parent_id = #{menuId} AND tenant_id = #{tenantId}")
    List<Long> findSubMenuIdByProductIdAndTenantId(@Param("menuId") Long menuId, @Param("tenantId") Long tenantId);
}
