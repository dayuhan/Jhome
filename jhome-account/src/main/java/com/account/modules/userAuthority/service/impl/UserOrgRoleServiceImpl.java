package com.account.modules.userAuthority.service.impl;

import com.account.modules.userAuthority.dao.UserOrgRoleMapper;
import com.account.modules.userAuthority.domain.UserOrganizationRole;
import com.account.modules.userAuthority.model.dto.AddUserOrgRoleMemberDTO;
import com.account.modules.userAuthority.model.dto.UserOrgRole;
import com.account.modules.userAuthority.model.dto.UserOrgRoleDTO;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.ListRoleMemberResponse;
import com.account.modules.userAuthority.service.UserOrgRoleService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author daxv
 * @date 2019/12/19 13:41
 */
@Service
public class UserOrgRoleServiceImpl extends ServiceImpl<UserOrgRoleMapper, UserOrganizationRole>implements UserOrgRoleService {

    @Autowired
    UserOrgRoleMapper userOrgRoleMapper;

    @Override
    public IPage<ListRoleMemberResponse> listRoleMember(CommonIdAndPageRequest request) {
        IPage<ListRoleMemberResponse> page = new Page<>(request.getPageNum(), request.getPageSize());
        return userOrgRoleMapper.listRoleMember(page, request.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoleMember(CommonIdsRequest request) {
        userOrgRoleMapper.deleteByIds(request.getIds());
    }

    @Override
    public IPage<ListRoleMemberResponse> listMemberExcludeRole(UserOrgRolePageRequest request) {
        IPage<ListRoleMemberResponse> page = new Page<>(request.getPageNum(), request.getPageSize());
        return userOrgRoleMapper.listMemberExcludeRole(page, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserOrgRoleMember(AddUserOrgRoleMemberRequset request) {
        UserOrganizationRole userOrganizationRole;
        for(AddUserOrgRoleMemberDTO addUserOrgRoleMemberDTO : request.getAddUserOrgRoleMemberList()) {
            userOrganizationRole = new UserOrganizationRole();
            userOrganizationRole.setId(addUserOrgRoleMemberDTO.getId());
            userOrganizationRole.setRoleId(request.getRoleId());
            this.save(userOrganizationRole);
        }
    }

    @Override
    public List<UserOrgRoleDTO> listUserOrgRoleByUserId(Long userId, Long tenantId) {
        return userOrgRoleMapper.listUserOrgRoleByUserId(userId, tenantId);
    }


    @Transactional
    @Override
    public void setDefaultByUserId(Long userId) {
        //修改用户组织
        UserOrganizationRole userOrganizationRole1 = new UserOrganizationRole();
        userOrganizationRole1.setDefaultFlag(0);
        LambdaUpdateWrapper<UserOrganizationRole> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(UserOrganizationRole::getUserId, userId);
        userOrgRoleMapper.update(userOrganizationRole1, lambdaUpdateWrapper);
    }

    @Transactional
    @Override
    public void setDefaultByUserIdAndTenantId(Long userId, Long tenantId) {
        //修改用户组织
        UserOrganizationRole userOrganizationRole = new UserOrganizationRole();
        userOrganizationRole.setDefaultFlag(0);
        LambdaUpdateWrapper<UserOrganizationRole> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(UserOrganizationRole::getUserId, userId);
        lambdaUpdateWrapper.eq(UserOrganizationRole::getTenantId, tenantId);
        userOrgRoleMapper.update(userOrganizationRole, lambdaUpdateWrapper);
    }

    @Transactional
    @Override
    public void setDefaultFlag(Long userId, Long tenantId, Long roleId) {
        //修改用户组织
        UserOrganizationRole userOrganizationRole = new UserOrganizationRole();
        userOrganizationRole.setDefaultFlag(1);
        LambdaUpdateWrapper<UserOrganizationRole> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(UserOrganizationRole::getUserId, userId);
        lambdaUpdateWrapper.eq(UserOrganizationRole::getTenantId, tenantId);
        lambdaUpdateWrapper.eq(UserOrganizationRole::getRoleId, roleId);
        userOrgRoleMapper.update(userOrganizationRole, lambdaUpdateWrapper);
    }

    @Override
    public Long findFirstRoleByUserIdAndTenantId(Long userId, Long tenantId) {
        return userOrgRoleMapper.findFirstRoleByUserIdAndTenantId(userId, tenantId);
    }

    @Override
    public UserOrgRole findFirstUserOrgRoleByUserIdAndTenantIdAndRoleId(UpdateUserTenantAndRoleRequest request) {
        return userOrgRoleMapper.findFirstUserOrgRoleByUserIdAndTenantIdAndRoleId(request);
    }



}
