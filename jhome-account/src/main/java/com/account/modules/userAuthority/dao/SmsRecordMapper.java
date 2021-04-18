package com.account.modules.userAuthority.dao;

import com.account.modules.userAuthority.domain.SmsRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SmsRecordMapper extends BaseMapper<SmsRecord> {

    @Select(value = " SELECT * FROM sms_record WHERE TO_DAYS(end_time) = TO_DAYS(NOW()) AND  mobile= #{mobile}")
    List<SmsRecord> listSmsRecordByMobile(@Param("mobile") String mobile);
}