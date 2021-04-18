package com.account.modules.userAuthority.service.impl;

import com.account.modules.userAuthority.dao.SysExceptionMapper;
import com.account.modules.userAuthority.domain.SysException;
import com.account.modules.userAuthority.model.request.SysExceptionRequest;
import com.account.modules.userAuthority.service.SysExceptionService;
import com.ar.common.util.DateUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfvape.uid.UidGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author daxv
 * @date 2019/10/8 11:10
 */
@Service
public class SysExceptionServiceImpl extends ServiceImpl<SysExceptionMapper, SysException> implements SysExceptionService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    UidGenerator uidGenerator;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSysException(SysExceptionRequest request) {
        SysException sysException = new SysException();
        BeanUtils.copyProperties(request, sysException);
        String curTime = DateUtils.getyyyyMMddHHmmss2();
        sysException.setId(uidGenerator.getUID());
        sysException.setCreateTime(curTime);
        sysException.setUpdateTime(curTime);
        sysException.setUpdateUserId(request.getCreateUserId());
        sysException.setDeleteFlag(0L);
        this.save(sysException);
    }
}
