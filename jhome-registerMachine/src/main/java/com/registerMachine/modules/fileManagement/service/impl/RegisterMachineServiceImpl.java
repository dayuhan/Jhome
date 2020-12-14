package com.registerMachine.modules.fileManagement.service.impl;


import com.bracket.common.register.EncryptHelper;
import com.registerMachine.autoconfiguration.SysConfigurationPropertiesBean;
import com.registerMachine.modules.fileManagement.model.query.RegisterQuery;
import com.registerMachine.modules.fileManagement.service.RegisterMachineService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class RegisterMachineServiceImpl implements RegisterMachineService {
    private final static Logger logger = Logger.getLogger(RegisterMachineServiceImpl.class.getName());

    @Autowired
    private SysConfigurationPropertiesBean propertiesBean;
    @Override
    public RegisterQuery getGenerateCode(String declareCode) {
        //
        RegisterQuery registerQuery=new RegisterQuery();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        //申请码
        Date startTime=new Date();
        Calendar calendar=Calendar.getInstance();
        //设置开始时间
        calendar.setTime(startTime);
        //设置到期时间
        calendar.add(Calendar.YEAR,Integer.valueOf(propertiesBean.getValidPeriod()));
        Date endTime=calendar.getTime();
        //生成秘钥串
        String secretKey= EncryptHelper.GetIv(16);

        //构造授权码 参数一 申请码
        StringBuilder sb=new StringBuilder();
        sb.append(declareCode);
        sb.append("_");

        //开始时间
        sb.append(simpleDateFormat.format(startTime));
        sb.append("_");

        //到期时间
        sb.append(simpleDateFormat.format(endTime));
        sb.append("_");

        //有效期
        sb.append(propertiesBean.getValidPeriod());
        sb.append("_");
        //产品编码
        sb.append(propertiesBean.getProductCode());

        //生成授权码
        String grantCode =EncryptHelper.AESEncrypt(sb.toString(),secretKey);
        logger.info("生成授权码："+grantCode);
        registerQuery.setGrantCode(grantCode);//生成授权码
        registerQuery.setRegisterCode(secretKey);//生成注册码
        return registerQuery;
    }
}
