package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.common.sbUtil.PageUtils;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.ListUserInfoRes;
import com.account.modules.userAuthority.model.response.LoginRes;
import com.account.modules.userAuthority.model.response.UcTokenRes;
import com.account.modules.userAuthority.service.UserInfoService;
import com.ar.common.exception.IllegalValidateException;
import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.util.StringUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bracket.common.Identity.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 用户信息
 */
@Api(value = "用户", tags = "Q、权限服务-用户管理")
@RestController
@Slf4j
@RequestMapping(value = "/uc/userInfo", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserInfoController  {

    @Autowired
    UserInfoService service;

    @PostMapping("/loginByAccount")
    @ApiOperation(value = "账号登录", notes = "Requires AccessToken")
    public CommonRlt<UcTokenRes> loginByAccount(@Validated @RequestBody LoginAccountReq request, HttpServletRequest httpRequest, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController loginByAccount show info ：" + request);
        return CommonRlt.success(service.loginByAccount(request,httpRequest));
    }

    @PostMapping("/loginByCode")
    @ApiOperation(value = "验证码登录", notes = "Requires AccessToken")
    public CommonRlt<UcTokenRes> loginByCode(@Validated @RequestBody LoginCodeReq request, HttpServletRequest httpRequest, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController loginByCode show info ：" + request);
        return CommonRlt.success(service.loginByCode(request,httpRequest));
    }

    @RequestMapping(value = "/mobileRegister", method = RequestMethod.POST)
    @ApiOperation(value = "手机号注册", notes = "user register")
    public CommonRlt register(@Validated @RequestBody AddUserRegister request, BindingResult result) throws Exception {
        log.info("UserInfoController mobileRegister show info ：" + request);
        return service.register(request);
    }

    @GetMapping(value = "/loginOut",headers="luxToken")
    @ApiOperation(value = "注销")
    @ApiImplicitParam(paramType="header",required=true,name="luxToken",value="用户认证凭据",defaultValue="PC-c84258e9c39059a89ab77d846ddab909-666-20200305140004-24efc2")
    public CommonRlt login(HttpServletRequest request) throws IllegalValidateException {
        log.info("UserInfoController loginOut show info ：" + request);
        return service.loginOut(request);
    }

    @RequestMapping(value = "/resetPasswords",headers="luxToken", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码", notes = "reset passwords")
    public CommonRlt resetPasswords(@Validated @RequestBody CommonIdsRequest request, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController resetPasswords show info ：" + request);
        return service.resetPasswords(request);
    }

    @RequestMapping(value = "/switchStatus",headers="luxToken", method = RequestMethod.POST)
    @ApiOperation(value = "启动/禁用", notes = "Start/disable")
    public CommonRlt switchStatus(@Validated @RequestBody EditUserStatusReq request, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController switchStatus show info ：" + request);
        return service.switchStatus(request);
    }

    @RequestMapping(value = "/batchDelete",headers="luxToken", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除(支持单删)", notes = "batch Delete")
    public CommonRlt batchDelete(@Validated @RequestBody CommonIdsRequest request, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController switchStatus show info ：" + request);
        return service.batchDelete(request);
    }

    @RequestMapping(value = "/findUserInfoList",headers="luxToken", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户列表", notes = "user info")
    public CommonRlt<PageUtils<List<ListUserInfoRes>>> findUserInfoList(@Validated @RequestBody ListUserReq request, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController findUserInfoList show info :" + request);
        //service.findUserList(request);
        IPage<ListUserInfoRes> pageInfo = service.findUserList(request);
        return CommonRlt.success(new PageUtils<>(pageInfo.getRecords(), pageInfo.getTotal(), request.getPageSize()));
    }

    @RequestMapping(value = "/getUserDetails", method = RequestMethod.GET,headers="luxToken")
    @ApiOperation(value = "获取用户详情", notes = "get user info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "query", value = "用户ID", required = true, dataType = "Long")
    })
    public CommonRlt getUserDetails(@RequestParam Long id) throws IllegalValidateException {
        log.info("UserInfoController getUserDetails show info :" + id);
        return service.getUserDetails(id);
    }

    @RequestMapping(value = "/addUser",headers="luxToken", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户", notes = "add user")
    public CommonRlt addUser(@Validated @RequestBody OperateUserReq request, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController addUser  show info ：" + request);
        //账号与手机2选一
        if (StringUtil.isEmpty(request.getLoginName()) && StringUtil.isEmpty(request.getMobile())) {
            return new CommonRlt(BasicRestStatusEnum.INVALID_MODEL_FIELDS, "账号或手机不能为空!");
        } else if (StringUtils.isNotBlank(request.getMobile()) && !StringUtil.verifyMobile(request.getMobile())) {
            return new CommonRlt(BasicRestStatusEnum.INVALID_MODEL_FIELDS, "请输入正确的手机号!");
        }
        return service.addUser(request);
    }

    @RequestMapping(value = "/editUser",headers="luxToken", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户", notes = "edit user ")
    public CommonRlt editUser(@Validated @RequestBody OperateUserReq request, BindingResult result) throws IllegalValidateException {
        log.info("UserInfoController editUser  show info ：" + request);
        if (StringUtil.isEmpty(request.getLoginName()) && StringUtil.isEmpty(request.getMobile())) {
            return new CommonRlt(BasicRestStatusEnum.INVALID_MODEL_FIELDS, "账号或手机不能为空!");
        } else if (StringUtils.isNotBlank(request.getMobile()) && !StringUtil.verifyMobile(request.getMobile())) {
            return new CommonRlt(BasicRestStatusEnum.INVALID_MODEL_FIELDS, "请输入正确的手机号!");
        }
        return service.editUser(request);
    }

    @PostMapping(value = "/updateUserOrg",headers="luxToken")
    @ApiOperation(value = "切换组织", notes = "Requires AccessToken")
    public CommonRlt<LoginRes> UpdateUserOrg(@Validated @RequestBody UpdateUserOrgRequest request, BindingResult result) throws IllegalValidateException {
        return CommonRlt.success(service.UpdateUserOrg(request));
    }

    @RequestMapping(value = "/userImport",headers="luxToken", method = RequestMethod.POST)
    @ApiOperation(value = "用户批量上传", notes = "User batch upload")
    public CommonRlt addBatchImport(Long orgId, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        log.info(" UserInfoController addBatchImport show info ：" + fileName);
        return service.addBatchImport(orgId, fileName, file);
    }

    @PostMapping(value = "/findUserToken",headers="luxToken")
    @ApiOperation(value = "获取用户令牌信息", notes = "Requires AccessToken")
    public CommonRlt<LoginRes> findUserToken(ServletRequest request, ServletResponse response) throws IllegalValidateException {
        String token= UserUtil.GetToken(request,response);
        return CommonRlt.success(service.findUserToken(token));
    }

}