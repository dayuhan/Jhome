package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.modules.userAuthority.model.request.BSMainStatusReq;
import com.account.modules.userAuthority.model.response.BSActivelyUserResponse;
import com.account.modules.userAuthority.model.response.BSBasicSummaryResponse;
import com.account.modules.userAuthority.service.BackupService;
import com.account.modules.userAuthority.service.TenantService;
import com.ar.common.rest.BasicRestStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Author: Da xv
 * Create date: 2019.12.24
 */
@Api(value = "公共接口", tags = {"Q、公共服务-后台首页统计"})
@RestController
@Slf4j
@RequestMapping(value = "/pub", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BSIndexController  {

    @Autowired
    TenantService tenantService;

    @Autowired
    BackupService backupService;
    @RequestMapping(value = "/sysIndexBasicInfoCalc", headers = "jhomeToken", method = RequestMethod.POST)
    @ApiOperation(value = "AVR后台首页基本数据统计：注册用户数，全校学习时长，本校资源，本校教材")
    public CommonRlt sysIndexBasicInfoCalc(@Validated @RequestBody BSMainStatusReq request) {
        log.info("BSIndexController sysIndexBasicInfoCalc show info : " + request);
        BSBasicSummaryResponse response = new BSBasicSummaryResponse();
        response.setRegistedUserNum(tenantService.calcRegistedUserNumByTenantd(request));
        response.setStudyDurationForWhole(tenantService.calcStudyDurationByTenantId(request));
        response.setResourcesForThisTenant(tenantService.calcResourceNumByTenantd(request));
        response.setTextBookForThisTenant(tenantService.calcTextBookNumByTenantd(request));

        return new CommonRlt<>(BasicRestStatusEnum.OK, response);
    }

    @RequestMapping(value = "/sysActivelyUsersCount", headers = "jhomeToken", method = RequestMethod.POST)
    @ApiOperation(value = "AVR后台首页基本数据统计：活跃用户数")
    public CommonRlt sysActivelyUsersCount(@Validated @RequestBody BSMainStatusReq request) {
        log.info("BSIndexController sysActivelyUsersCount show info : " + request);
        BSActivelyUserResponse response = new BSActivelyUserResponse();
        response.setOneDayActively(tenantService.dailyTheMostActivelyUsersCount(request));
        response.setOneWeekDayActively(tenantService.weeklyTheMostActivelyUsersCount(request));
        response.setOneMonthActively(tenantService.monthlyTheMostActivelyUsersCount(request));

        return new CommonRlt<>(BasicRestStatusEnum.OK, response);
    }

    @ApiOperation(value = "数据库备份，备份文件")
    @RequestMapping(value = "/backup/new", method = RequestMethod.GET)
    public void backupNew(HttpServletResponse response) {
        backupService.backupNew(response);
    }


}
