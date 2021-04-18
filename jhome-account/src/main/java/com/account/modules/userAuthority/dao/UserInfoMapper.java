package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.User;
import com.account.modules.userAuthority.domain.UserOrganizationRole;
import com.account.modules.userAuthority.model.dto.EditUserDto;
import com.account.modules.userAuthority.model.dto.RoleListDTO;
import com.account.modules.userAuthority.model.dto.UserDetailsRoleDTO;
import com.account.modules.userAuthority.model.request.ListUserOrgIdsReq;
import com.account.modules.userAuthority.model.request.ListUserReq;
import com.account.modules.userAuthority.model.request.UserInfoReq;
import com.account.modules.userAuthority.model.response.ListUserInfoRes;
import com.account.modules.userAuthority.model.response.LoginRes;
import com.account.modules.userAuthority.model.response.UserInfoBackstageRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
public interface UserInfoMapper extends BaseMapper<User> {

    public Integer updateByIds(@Param("request") EditUserDto request);

    IPage<ListUserInfoRes> findByUserList(IPage<ListUserInfoRes> page, @Param("request") ListUserReq request);

    IPage<ListUserInfoRes> findByUserOrgIdsList(IPage<ListUserInfoRes> page, @Param("request") ListUserOrgIdsReq request);

    public UserInfoBackstageRes getUserDetails(@Param("id") Long id);

    public User findByUserName(@Param("request") UserInfoReq request);

    public List<UserOrganizationRole> findUserOrgRoleList(@Param("id") Long id);

    public IPage<UserOrganizationRole> findUserOrgRoles(IPage<UserOrganizationRole> page, @Param("id") Long id);

    public RoleListDTO getRoleInfo(@Param("id") Long id);

    public UserDetailsRoleDTO getOrgInfo(@Param("id") Long id);

    public Integer deleteUserOrgRole(@Param("ids") Long[] ids);

    public List<UserOrganizationRole> findUserOrgRoleIdList(@Param("id") Long id, @Param("userId") Long userId);

    List<LoginRes> findByLoginName(@Param("userName") String userName);

    List<LoginRes> findUserById(@Param("id") Long id);

    Long isExistNo(@Param("id") Long id, @Param("no") String no, @Param("tenantId") Long tenantId);
}
