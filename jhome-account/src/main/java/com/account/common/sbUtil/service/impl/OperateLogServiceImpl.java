package com.account.common.sbUtil.service.impl;/*
package com.account.common.sbUtil.service.impl;

import com.account.common.sbUtil.dao.OperateLogMapper;
import com.account.common.sbUtil.domain.OperateLog;
import com.account.common.sbUtil.service.OperateLogService;
import com.ar.common.util.DateUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfvape.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

*/
/**
 * @author daxv
 * @date 2019/12/13 10:08
 *//*

@Service
@Component
@Async("taskExecutor")
@Slf4j
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog>  implements OperateLogService {
    @Autowired
    UidGenerator uidGenerator;

    @Override
    public void saveOperateLog(OperateLog operateLog) {
        operateLog.setId(uidGenerator.getUID());
        operateLog.setCreateTime(DateUtils.getyyyyMMddHHmmss2());
        this.save(operateLog);
    }
}
*/
