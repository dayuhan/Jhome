package com.account.modules.userAuthority.service.impl;

import com.account.modules.userAuthority.dao.TenantMapper;
import com.account.modules.userAuthority.domain.LoginLog;
import com.account.modules.userAuthority.domain.Tenant;
import com.account.modules.userAuthority.domain.UserStudyLog;
import com.account.modules.userAuthority.model.request.BSMainStatusReq;
import com.account.modules.userAuthority.service.TenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author: Da xv
 * Create date: 2019.12.24
 */
@Slf4j
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {
    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public Integer calcRegistedUserNumByTenantd(BSMainStatusReq request) {
        return tenantMapper.calcUserNumByTenantId(request.getTenantId());
    }

    @Override
    public Integer calcTextBookNumByTenantd(BSMainStatusReq request) {
        return tenantMapper.calcTextBookNumByTenantId(request.getTenantId());
    }

    @Override
    public Integer calcResourceNumByTenantd(BSMainStatusReq request) {
        return tenantMapper.calcResourceNumByTenantId(request.getTenantId());
    }

    @Override
    public Integer calcStudyDurationByTenantId(BSMainStatusReq request) {
        List<UserStudyLog> data = tenantMapper.calcStudyDurationByTenantId(request.getTenantId());
        Integer result = 0;

        for(UserStudyLog eachD : data) {
            result += eachD.getTotalTime();
        }

        return result;
    }

    @Override
    public Integer dailyTheMostActivelyUsersCount(BSMainStatusReq request) {
        List<LoginLog> result = tenantMapper.queryLoginLogByTimeGap(1, request.getTenantId());
        List<Integer> colAry = new ArrayList();

        for (LoginLog eachLog : result) {
            if(colAry.stream().filter((Integer num) -> num.equals(eachLog.getUserId())).count() < 1) {
                colAry.add(eachLog.getUserId());
            }
        }

        return colAry.size();
    }
    @Override
    public Integer weeklyTheMostActivelyUsersCount(BSMainStatusReq request) {
        List<LoginLog> result = tenantMapper.queryLoginLogByTimeGap(7, request.getTenantId());
        return extraCalculateForWM(result, 7);
    }
    @Override
    public Integer monthlyTheMostActivelyUsersCount(BSMainStatusReq request) {
        List<LoginLog> result = tenantMapper.queryLoginLogByTimeGap(30, request.getTenantId());
        return extraCalculateForWM(result, 30);
    }

    private Integer extraCalculateForWM(List<LoginLog> data, Integer gap) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());

        List<String> dates = new ArrayList();
        for(int i = gap; i > 0; i--) {
            gc.add(5, -1);
            dates.add(gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH)+1) + "-" + gc.get(Calendar.DATE));
        }

        List<Integer> uIds = new ArrayList();
        Integer calcNum = 0;

        for (LoginLog eachLL : data) {
            if(!uIds.contains(eachLL.getUserId())) { uIds.add(eachLL.getUserId()); }
        }

        boolean flag;
        for (Integer uid : uIds) {
            flag = true;
            for (String ss : dates) {
                if(data.stream().filter((LoginLog ll) -> ll.getUserId().equals(uid) && ll.getCreateTime().equals(ss)).count() < 1) {
                    flag = false;
                    break;
                }
            }
            if(flag) { calcNum += 1; }
        }

        return calcNum;
    }
}
