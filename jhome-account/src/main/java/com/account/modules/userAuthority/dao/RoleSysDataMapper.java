package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.RoleSysData;
import com.account.modules.userAuthority.domain.RoleSysMenu;
import com.account.modules.userAuthority.model.request.CommonUserIdRequest;
import com.account.modules.userAuthority.model.response.GetSysDataResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleSysDataMapper extends BaseMapper<RoleSysData> {
    /**
     * 获取用户所有数据橘色
     */
    List<GetSysDataResponse> findRoleDataByUserId(@Param("request") CommonUserIdRequest request);
}