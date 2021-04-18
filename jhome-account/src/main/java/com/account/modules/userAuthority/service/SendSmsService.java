package com.account.modules.userAuthority.service;

import com.account.common.CommonRlt;
import com.account.modules.userAuthority.domain.SmsRecord;
import com.account.modules.userAuthority.model.request.SmsMsgRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
public interface SendSmsService extends IService<SmsRecord> {

    CommonRlt smsCaptcha(SmsMsgRequest request)throws Exception;

}
