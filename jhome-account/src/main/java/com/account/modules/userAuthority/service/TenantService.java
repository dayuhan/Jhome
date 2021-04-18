package com.account.modules.userAuthority.service;

import com.account.modules.userAuthority.domain.Tenant;
import com.account.modules.userAuthority.model.request.BSMainStatusReq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Author: Da xv
 * Create date: 2019.12.24
 */
public interface TenantService extends IService<Tenant> {
    Integer calcRegistedUserNumByTenantd(BSMainStatusReq request);

    Integer calcTextBookNumByTenantd(BSMainStatusReq request);

    Integer calcResourceNumByTenantd(BSMainStatusReq request);

    Integer calcStudyDurationByTenantId(BSMainStatusReq request);

    Integer dailyTheMostActivelyUsersCount(BSMainStatusReq request);

    Integer weeklyTheMostActivelyUsersCount(BSMainStatusReq request);

    Integer monthlyTheMostActivelyUsersCount(BSMainStatusReq request);
}
