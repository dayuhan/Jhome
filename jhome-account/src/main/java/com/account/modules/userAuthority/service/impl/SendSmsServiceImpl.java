package com.account.modules.userAuthority.service.impl;

import com.account.common.CommonRlt;
import com.account.common.sbUtil.util.SendSmsUtil;
import com.account.modules.userAuthority.dao.SmsRecordMapper;
import com.account.modules.userAuthority.domain.SmsRecord;
import com.account.modules.userAuthority.model.request.SmsMsgRequest;
import com.account.modules.userAuthority.service.SendSmsService;
import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.util.DateUtils;
import com.ar.common.util.RandomStringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl; 
import com.xfvape.uid.UidGenerator;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Service
@Log4j
public class SendSmsServiceImpl extends ServiceImpl<SmsRecordMapper, SmsRecord> implements SendSmsService {

    @Autowired
    SmsRecordMapper smsRecordMapper;

    @Autowired
    UidGenerator uidGenerator;

    private final int COUNT = 4;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonRlt smsCaptcha(SmsMsgRequest request) throws Exception {
        //获取集合信息
        List<SmsRecord> list = smsRecordMapper.listSmsRecordByMobile(request.getMobile());
        if (list.size() > COUNT) {
            log.error(BasicRestStatusEnum.EXCEED_CAPTCHA_MAXIMUM.subMessage());
            return new CommonRlt(BasicRestStatusEnum.EXCEED_CAPTCHA_MAXIMUM);
        }
        SendSmsUtil smsUtil = new SendSmsUtil();
        String templateId = smsUtil.templateCode;
        String code = RandomStringUtils.getRandomNum(6);
        String beginDate = DateUtils.getyyyyMMddHHmmss2();
        Boolean bo = smsUtil.sendAliMessage(request.getMobile(),code);
        if (bo == false || bo == null) {
            log.error(BasicRestStatusEnum.ERROR_NETWORK_FAST.subMessage());
            return new CommonRlt(BasicRestStatusEnum.ERROR_NETWORK_FAST);
        }
        String endTime = DateUtils.getyyyyMMddHHmmss2();
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setId(uidGenerator.getUID());
        smsRecord.setSmsType((long) 1);
        smsRecord.setMobile(request.getMobile());
        smsRecord.setTemplateId(templateId);
        smsRecord.setBeginTime(beginDate);
        smsRecord.setEndTime(endTime);
        smsRecord.setOperationFlag(request.getOperationFlag());
        smsRecord.setDeleteFlag(0);
        smsRecord.setCaptcha(code);
        this.save(smsRecord);
        return new CommonRlt(BasicRestStatusEnum.OK);

    }
}
