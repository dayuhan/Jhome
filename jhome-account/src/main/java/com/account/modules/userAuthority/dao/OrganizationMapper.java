package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.Organization;
import com.account.modules.userAuthority.domain.UserOrganizationRole;
import com.account.modules.userAuthority.model.dto.OrganizationDTO;
import com.account.modules.userAuthority.model.request.CommonIdsRequest;
import com.account.modules.userAuthority.model.request.EditOrganizationReq;
import com.account.modules.userAuthority.model.response.ListOrgUserNumRes;
import com.account.modules.userAuthority.model.response.ListOrganizationRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {

    public Integer addOrganization(@Param("org") Organization org);

    IPage<ListOrganizationRes> findOrganizationList(IPage<ListOrganizationRes> page, @Param("org") Organization org);

    public List<ListOrganizationRes> findByChildrenList(@Param("id") Long id);

    public List<ListOrganizationRes> findByOrgList(@Param("org") Organization org);

    public Integer updateByOrg(@Param("request") EditOrganizationReq request);

    public Integer updateByIds(@Param("request") OrganizationDTO request);

    public Organization findOrgDetails(@Param("id") Long id);

    public Organization findAppOrgList(@Param("id") Long id);

    public Organization findOrgByCode(@Param("code") String code);

    public UserOrganizationRole findUserOrgRole(@Param("userId") Long userId, @Param("orgId") Long orgId);

    public Integer deleteUserOrg(@Param("id") Long id);

    public List<ListOrgUserNumRes> findOrgUserNum(@Param("ids") CommonIdsRequest ids);
}