package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.UserOrganizationRole;
import com.account.modules.userAuthority.model.dto.UserOrgRole;
import com.account.modules.userAuthority.model.dto.UserOrgRoleDTO;
import com.account.modules.userAuthority.model.dto.UserTenantRoleDTO;
import com.account.modules.userAuthority.model.request.UpdateUserTenantAndRoleRequest;
import com.account.modules.userAuthority.model.request.UserOrgRolePageRequest;
import com.account.modules.userAuthority.model.request.UserTenantRequest;
import com.account.modules.userAuthority.model.response.ListRoleMemberResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserOrgRoleMapper extends BaseMapper<UserOrganizationRole> {
    IPage<ListRoleMemberResponse> listRoleMember(IPage<ListRoleMemberResponse> page, @Param(value = "roleId") Long roleId);

    void deleteByIds(@Param(value = "ids") Long[] ids);

    IPage<ListRoleMemberResponse> listMemberExcludeRole(IPage<ListRoleMemberResponse> page, @Param("request") UserOrgRolePageRequest request);

    //    @Query(value="SELECT 1 FROM user_organization_role ur where CAST(ur.role_id AS CHAR) = (select sa.reserve1 from  sys_arg sa) AND ur.user_id= ?1",nativeQuery=true)
    @Select(value="SELECT 1 FROM user_organization_role ur where ur.role_id = (select sa.platform_super_manager_role_id from  sys_arg sa) AND ur.user_id= #{userId}")
    Long findSuperUserId(@Param("userId") Long userId);


    @Select(value="select GROUP_CONCAT(DISTINCT r.role_name) from user_organization_role uor left join role r on uor.role_id = r.id where r.delete_flag = 0 and uor.user_id = #{userId}")
    String getRoleNameByUserId(@Param("userId") Long userId);

    @Select(value="select GROUP_CONCAT(DISTINCT r.role_name) from user_organization_role uor left join role r on uor.role_id = r.id where r.delete_flag = 0 and uor.user_id = #{userId} and r.tenant = #{tenantId}")
    String getRoleNameByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

    List<UserOrgRoleDTO> listUserOrgRoleByUserId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

    Long findFirstRoleByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

    UserOrgRole findFirstUserOrgRoleByUserIdAndTenantIdAndRoleId(@Param("request") UpdateUserTenantAndRoleRequest request);

    List<UserTenantRoleDTO> GetUserTenantListByUserId(@Param("request") UserTenantRequest request);
}
