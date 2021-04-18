package com.account.modules.userAuthority.service;

import com.account.modules.userAuthority.domain.UserOrganizationRole;
import com.account.modules.userAuthority.model.dto.UserOrgRole;
import com.account.modules.userAuthority.model.dto.UserOrgRoleDTO;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.ListRoleMemberResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserOrgRoleService extends IService<UserOrganizationRole> {

    IPage<ListRoleMemberResponse> listRoleMember(CommonIdAndPageRequest request);

    void deleteRoleMember(CommonIdsRequest request);

    IPage<ListRoleMemberResponse> listMemberExcludeRole(UserOrgRolePageRequest request);

    void addUserOrgRoleMember(AddUserOrgRoleMemberRequset request);

    List<UserOrgRoleDTO> listUserOrgRoleByUserId(Long userId, Long tenantId);

    void setDefaultByUserId(Long userId);

    void setDefaultByUserIdAndTenantId(Long userId, Long tenantId);

    void setDefaultFlag(Long userId, Long tenantId, Long roleId);

    Long findFirstRoleByUserIdAndTenantId(Long userId, Long tenantId);

    UserOrgRole findFirstUserOrgRoleByUserIdAndTenantIdAndRoleId(UpdateUserTenantAndRoleRequest request);

}
