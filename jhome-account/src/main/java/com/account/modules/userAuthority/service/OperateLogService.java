package com.account.modules.userAuthority.service;

import com.account.common.sbUtil.domain.OperateLog;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @author
 * @date
 */
public interface OperateLogService extends IService<OperateLog> {
    void saveOperateLog(OperateLog operateLog);
}
