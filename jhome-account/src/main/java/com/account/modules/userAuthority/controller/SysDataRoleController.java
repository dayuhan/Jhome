package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.common.sbUtil.PageUtils;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.GetRoleResponse;
import com.account.modules.userAuthority.model.response.GetRoleSysMenuResponse;
import com.account.modules.userAuthority.model.response.GetSysDataResponse;
import com.account.modules.userAuthority.model.response.GetSysMenuResponse;
import com.account.modules.userAuthority.service.SysDataRoleService;
import com.account.modules.userAuthority.service.SysMenuRoleService;
import com.ar.common.rest.BasicRestStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色数据权限
 */
@Api(value = "角色数据权限", tags = "Q、权限服务-角色数据权限")
@RestController
@RequestMapping(value = "/role", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class SysDataRoleController {

    @Autowired
    private SysDataRoleService sysDataRoleService;


    @ApiOperation("角色分配数据权限")
    @RequestMapping(value = "/addRoleSysData", headers = "luxToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt addRoleSysData(@Validated @RequestBody AddRoleSysDataRequest request, BindingResult result) {
        sysDataRoleService.addRoleSysData(request);
        return CommonRlt.success();
    }

    @ApiOperation("获取用户数据权限")
    @RequestMapping(value = "/getUserRoleSysData",headers="luxToken", method = RequestMethod.POST)
    @ResponseBody
    public  CommonRlt<List<GetSysDataResponse>> getUserRoleSysData(@Validated @RequestBody CommonUserIdRequest request, BindingResult result) {
        CommonRlt<List<GetSysDataResponse>> commonRlt = new CommonRlt<>(BasicRestStatusEnum.OK, sysDataRoleService.findRoleDataByUserId(request));
        return commonRlt;
    }

    @ApiOperation("获取用户数据权限列表")
    @RequestMapping(value = "/getUserRoleSysDataList",headers="luxToken", method = RequestMethod.POST)
    @ResponseBody
    public  CommonRlt<List<GetSysDataResponse>> getUserRoleSysDataList(@Validated @RequestBody AddRoleSysDataRequest request, BindingResult result) {
        CommonRlt<List<GetSysDataResponse>> commonRlt = new CommonRlt<>(BasicRestStatusEnum.OK, sysDataRoleService.getUserRoleSysDataList(request));
        return commonRlt;
    }

}
