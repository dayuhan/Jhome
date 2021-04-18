package com.account.modules.userAuthority.service.impl;

import com.account.modules.userAuthority.dao.LoginLogMapper;
import com.account.modules.userAuthority.domain.LoginLog;
import com.account.modules.userAuthority.model.dto.LoginStatusEnum;
import com.account.modules.userAuthority.service.LoginLogService;
import com.ar.common.util.DateUtils;
import com.ar.common.util.JsonUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfvape.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Component
@Async("taskExecutor")
@Slf4j
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {


    @Resource
    UidGenerator uidGenerator;

    @Override
    public void loginIn(Integer userId, String loginIp, Integer loginDevice, String loginSource, String equipmentType, Long tenantId) {
        LoginLog loginLog = new LoginLog();
        loginLog.setId(uidGenerator.getUID());
        loginLog.setUserId(userId);
        loginLog.setStatus(LoginStatusEnum.LOGININ);
        loginLog.setCreateTime(DateUtils.getyyyyMMddHHmmss2());
        loginLog.setLoginIp(loginIp);
        loginLog.setLoginDevice(loginDevice);
        loginLog.setLoginSource(loginSource);
        loginLog.setEquipmentType(equipmentType);
        loginLog.setTenantId(tenantId);
        log.info("login_log:"+ JsonUtils.parse(loginLog));
        this.save(loginLog);
    }

    @Override
    public void loginOut(Integer userId) {
        LoginLog loginLog = new LoginLog();
        loginLog.setId(uidGenerator.getUID());
        loginLog.setUserId(userId);
        loginLog.setStatus(LoginStatusEnum.LOGINOUT);
        loginLog.setCreateTime(DateUtils.getyyyyMMddHHmmss2());
        this.save(loginLog);
    }
}
