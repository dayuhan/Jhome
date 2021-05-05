package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.common.sbUtil.PageUtils;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.ListOrgUserNumRes;
import com.account.modules.userAuthority.model.response.ListOrganizationRes;
import com.account.modules.userAuthority.service.OrganizationService;
import com.ar.common.exception.IllegalValidateException;
import com.ar.common.rest.BasicRestStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Api(value = "组织架构", tags = {"Q、权限服务-组织架构管理"})
@RestController
@Slf4j
@RequestMapping(value = "/organization", produces = {MediaType.APPLICATION_JSON_VALUE})
public class OrganizationController  {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    StringEncryptor stringEncryptor;

    @RequestMapping(value = "/findOrganizationList",headers="jhomeToken", method = RequestMethod.POST)
    @ApiOperation(value = "查询组织架构列表", notes = "Query the list of organizational structures")
    public CommonRlt<List<ListOrganizationRes>> findOrganizationList(@Validated @RequestBody ListOrganizationReq request, BindingResult result) throws IllegalValidateException {
        log.info("OrganizationController findOrganizationList  show info ：" + request);
        IPage<ListOrganizationRes> pageInfo = organizationService.findOrganizationList(request);
        PageUtils<List<ListOrganizationRes>> pageUtils = new PageUtils<>(pageInfo.getRecords(), pageInfo.getTotal(), request.getPageSize());
        return new CommonRlt(BasicRestStatusEnum.OK, pageUtils);

    }

    @RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
    @ApiOperation(value = "添加组织架构", notes = "add organizational structure")
    public CommonRlt addOrganization(@Validated @RequestBody AddOrganizationReq request, BindingResult result) throws IllegalValidateException {
        log.info("OrganizationController addOrganization  show info ：" + request);
        return organizationService.addOrganization(request);
    }

    @RequestMapping(value = "/editOrganization", headers = "jhomeToken",method = RequestMethod.POST)
    @ApiOperation(value = "修改组织架构", notes = "edit organizational structure")
    public CommonRlt editOrganization(@Validated @RequestBody EditOrganizationReq request, BindingResult result) throws IllegalValidateException {
        log.info("OrganizationController editOrganization  show info ：" + request);
        return organizationService.editOrganization(request);
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除(支持单删)", notes = "batch Delete")
    public CommonRlt batchDelete(@Validated @RequestBody CommonIdsRequest request, BindingResult result) throws IllegalValidateException {
        log.info("OrganizationController batchDelete show info ：" + request);
        return organizationService.batchDelete(request);
    }

    @RequestMapping(value = "/findOrgDetails", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "query", value = "组织ID", required = true, dataType = "Long")
    })
    @ApiOperation(value = "查询组织详情", notes = "Query organization name")
    public CommonRlt findOrgDetails(@RequestParam Long id) throws IllegalValidateException {
        log.info(" OrganizationController findOrgDetails show info ：" + id);
        return organizationService.findOrgDetails(id);
    }

    @RequestMapping(value = "/deleteUserOrg", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户学校关系(APP)", notes = "Delete user school relationship")
    public CommonRlt deleteUserOrg(@Validated @RequestBody CommonIdRequest request, BindingResult result) throws IllegalValidateException {
        log.info("OrganizationController addUserOrg  show info ：" + request);
        return organizationService.deleteUserOrg(request);
    }

    @PostMapping(value = "/findOrgUserNum")
    @ApiOperation(value = "获取组织分类用户数", notes = "Requires AccessToken")
    public CommonRlt<List<ListOrgUserNumRes>> findOrgUserNum(@Validated @RequestBody OrgUserRoleNum request, BindingResult result) throws IllegalValidateException {
        log.info(" OrganizationController findOrgUserNum show info ：" + request);
        return CommonRlt.success(organizationService.findOrgUserNum(request));
    }
}