package com.account.modules.userAuthority.service.impl;

import com.account.common.sbUtil.util.UserAgentUtil;
import com.account.modules.userAuthority.model.response.LoginRes;
import com.account.modules.userAuthority.service.RedisService;
import com.account.modules.userAuthority.service.TokenService;
import com.alibaba.fastjson.JSON;
import com.ar.common.util.DateUtils;
import com.ar.common.util.MD5Util;
import com.ar.common.util.StringUtil;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @author zhiqiang.liu1
 */
@Service
@Log4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisService redisService;

    private long expire1 = SESSION_UNIT_HOUR;

    private long expire2 = SESSION_UNIT_DAY;

    public final String TOKEN_PREFIX_PC = "PC-";
    /**
     * 统一加入 uc_token前缀标识
     */
    private String tokenPrefix = "uc_token:";

    @Override
    public String generateToken(String agent, LoginRes user) {
        try {
            UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(
                    agent);
            StringBuilder sb = new StringBuilder();
            //前缀标识
            sb.append(tokenPrefix);
            //解析---如果无法识别会生成手机端
            if (userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)) {
                if (UserAgentUtil.CheckAgent(agent)) {
                    sb.append("MOBILE-");
                } else {
                    sb.append("PC-");
                }
            } else if (userAgentInfo.getDeviceType()
                    .equals("Personal computer")) {
                sb.append("PC-");
            } else {
                sb.append("MOBILE-");
            }
            //加密用户名称loginName
            sb.append(MD5Util.getMd5(user.getRealName(), 32) + "-");
            sb.append(user.getUserId() + "-");
            //yyyyMMddHHmmss
            sb.append(DateUtils.getyyyyMMddHHmmss() + "-");
            //客户端简化实现——6位MD5码
            sb.append(MD5Util.getMd5(agent, 6));
            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(String token, LoginRes user) {
        //PC-
        if (token.startsWith(tokenPrefix + TOKEN_PREFIX_PC)) {
            redisService.set(token, JSON.toJSONString(user), expire1, TimeUnit.HOURS);
        } else {
            redisService.set(token,JSON.toJSONString(user),expire2, TimeUnit.DAYS);
        }
    }

    @Override
    public LoginRes getLoadUser(String token) {
        //PC-
        if (token.startsWith(tokenPrefix + TOKEN_PREFIX_PC)) {
            redisService.expire(token, expire1, TimeUnit.HOURS);
        } else {
            redisService.expire(token,expire2, TimeUnit.DAYS);
        }
        return JSON.parseObject(redisService.get(token), LoginRes.class);
    }

    @Override
    public void delete(String token) {
        if(StringUtil.isNotEmpty(token)){
            StringBuilder sb = new StringBuilder();
            sb.append(tokenPrefix);
            sb.append(token);
            redisService.remove(sb.toString());
        }
    }

    @Override
    public boolean validate(String agent, String token) {
        if (StringUtil.isEmpty(token)) {
            return false;
        }
        try {
            if(!token.startsWith(tokenPrefix)){
                token= tokenPrefix + token;
            }

            LoginRes user= JSON.parseObject(redisService.get(token), LoginRes.class);
            if(user == null){
                log.error("timeout token data:" + token);
                return false;
            }
            //时间 72(bai-w)
            Date TokenGenTime;
            String ucAgentMD5;
            String[] tokenDetails = token.split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            TokenGenTime = formatter.parse(tokenDetails[3]);
            long passed = Calendar.getInstance().getTimeInMillis()
                    - TokenGenTime.getTime();
                if (passed > SESSION_TIMEOUT * 1000) {
                    log.error("timeout token data:" + token);
                    return false;
                }
            ucAgentMD5 = tokenDetails[4];
            if(MD5Util.getMd5(agent, 6).equals(ucAgentMD5)) {
                return true;
            }
        } catch (ParseException e) {
            log.error("validate token Exception warning ...");
            return false;
        }
        return false;
    }
}
