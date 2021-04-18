package com.account.modules.userAuthority.service;


import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.GetRoleResponse;
import com.account.modules.userAuthority.model.response.GetRoleSysMenuResponse;
import com.account.modules.userAuthority.model.response.GetSysMenuResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;


public interface SysMenuRoleService {
    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<GetSysMenuResponse> getSysMenuByUserId(CommonUserIdRequest request);

    void addUserRole(AddUserOrgRoleRequest request);

    void addOrUpdateMenu(AddOrUpdateMenuRequest request);

    void addOrUpdateButton(AddOrUpdateButtonRequest request);

    void addRoleSysMenu(AddRoleSysMenuRequest request);

    void addRole(AddRoleRequest request);

    void editRole(EditRoleRequest request);

    void deleteRole(CommonIdRequest request);

    IPage<GetRoleResponse> listRole(ListSysMenuRequest request);

    void deleteMenu(CommonProductAndTenantIdRequest request);

    List<GetRoleSysMenuResponse> listRoleMenu(CommonProductAndTenantIdRequest request);

    void addSysDefaultRole(Long createUserId, Long tenantId, String tenantName);

    void addDefaultRoleSysMenu(Long teacherRoleId, Long managerRoleId);

    List<GetRoleResponse> listTenantRole(Long tenantId);
}
