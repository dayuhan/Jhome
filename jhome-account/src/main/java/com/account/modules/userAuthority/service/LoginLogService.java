package com.account.modules.userAuthority.service;


import com.account.modules.userAuthority.domain.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginLogService extends IService<LoginLog> {

    void loginIn(Integer userId, String loginIp, Integer loginDevice, String loginSource, String equipmentType, Long tenantId);

    void loginOut(Integer userId);

}
