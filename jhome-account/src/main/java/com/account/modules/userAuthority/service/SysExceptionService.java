package com.account.modules.userAuthority.service;

import com.account.modules.userAuthority.domain.SysException;
import com.account.modules.userAuthority.model.request.SysExceptionRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author daxv
 * @date 2019/10/8 11:10
 */
public interface SysExceptionService extends IService<SysException> {

    void addSysException(SysExceptionRequest request);
}
