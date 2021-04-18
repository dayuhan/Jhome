package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.LoginLog;
import com.account.modules.userAuthority.domain.Tenant;
import com.account.modules.userAuthority.domain.UserStudyLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {

    Integer calcUserNumByTenantId(@Param("tenantId") Long tenantId);

    Integer calcTextBookNumByTenantId(@Param("tenantId") Long tenantId);

    Integer calcResourceNumByTenantId(@Param("tenantId") Long tenantId);

    List<UserStudyLog> calcStudyDurationByTenantId(@Param("tenantId") Long tenantId);

    List<LoginLog> queryLoginLogByTimeGap(@Param("gapDayNum") Integer gapDayNum, @Param("tenantId") Long tenantId);
}
