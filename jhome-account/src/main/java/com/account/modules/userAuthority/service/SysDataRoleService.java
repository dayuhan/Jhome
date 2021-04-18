package com.account.modules.userAuthority.service;


import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.GetRoleResponse;
import com.account.modules.userAuthority.model.response.GetSysDataResponse;
import com.account.modules.userAuthority.model.response.GetSysMenuResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;


public interface SysDataRoleService {
    /**
     * 获取用户所有数据橘色
     */
    List<GetSysDataResponse> findRoleDataByUserId(CommonUserIdRequest request);

    /**
     * 分配数据角色
     * @param request
     */
    void addRoleSysData(AddRoleSysDataRequest request);

    /**
     *  根据角色获取数据权限
     * @param request
     * @return
     */
    List<GetSysDataResponse> getUserRoleSysDataList(AddRoleSysDataRequest request);

}
