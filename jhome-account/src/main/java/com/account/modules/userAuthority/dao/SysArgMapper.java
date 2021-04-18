package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.SysArg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface SysArgMapper extends BaseMapper<SysArg> {
    @Select(value="SELECT sa.platform_super_manager_role_id FROM sys_arg sa")
    Long findSuperRoleId();
}