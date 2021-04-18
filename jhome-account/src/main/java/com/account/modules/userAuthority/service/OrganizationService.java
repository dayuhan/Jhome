package com.account.modules.userAuthority.service;

import com.account.common.CommonRlt;
import com.account.modules.userAuthority.domain.Organization;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.ListOrgUserNumRes;
import com.account.modules.userAuthority.model.response.ListOrganizationRes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
public interface OrganizationService extends IService<Organization> {

    CommonRlt addOrganization(AddOrganizationReq request);

    IPage<ListOrganizationRes> findOrganizationList(ListOrganizationReq request);

    CommonRlt editOrganization(EditOrganizationReq request);

    CommonRlt batchDelete(CommonIdsRequest request);

    CommonRlt findOrgDetails(Long id);

    CommonRlt deleteUserOrg(CommonIdRequest request);

    List<ListOrgUserNumRes>  findOrgUserNum(OrgUserRoleNum req);
}