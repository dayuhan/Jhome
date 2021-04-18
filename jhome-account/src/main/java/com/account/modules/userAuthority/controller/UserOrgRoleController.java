package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.common.sbUtil.PageUtils;
import com.account.modules.userAuthority.model.request.AddUserOrgRoleMemberRequset;
import com.account.modules.userAuthority.model.request.CommonIdAndPageRequest;
import com.account.modules.userAuthority.model.request.CommonIdsRequest;
import com.account.modules.userAuthority.model.request.UserOrgRolePageRequest;
import com.account.modules.userAuthority.model.response.ListRoleMemberResponse;
import com.account.modules.userAuthority.service.UserOrgRoleService;
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


@Api(value = "用户组织角色管理", tags = "Q、权限服务-用户组织角色管理")
@RestController
@RequestMapping(value = "/userOrgRole", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class UserOrgRoleController   {

    @Autowired
    private UserOrgRoleService userOrgRoleService;

    @ApiOperation("获取角色的成员列表")
    @RequestMapping(value = "/listRoleMember",headers="luxToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt<PageUtils<List<ListRoleMemberResponse>>> listRoleMember(@Validated @RequestBody CommonIdAndPageRequest request, BindingResult result) {
        IPage<ListRoleMemberResponse> pageInfo = userOrgRoleService.listRoleMember(request);
        return CommonRlt.success(new PageUtils<>(pageInfo.getRecords(), pageInfo.getTotal(), request.getPageSize()));
    }

    @ApiOperation("删除角色的成员")
    @RequestMapping(value = "/deleteRoleMember",headers="luxToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt deleteRoleMember(@Validated @RequestBody CommonIdsRequest request, BindingResult result) {
        userOrgRoleService.deleteRoleMember(request);
        return CommonRlt.success();
    }

    @ApiOperation("获取成员列表不包含此角色的成员")
    @RequestMapping(value = "/listMemberExcludeRole",headers="luxToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt<PageUtils<List<ListRoleMemberResponse>>> listMemberExcludeRole(@Validated @RequestBody UserOrgRolePageRequest request, BindingResult result) {
        IPage<ListRoleMemberResponse> pageInfo = userOrgRoleService.listMemberExcludeRole(request);
        return CommonRlt.success(new PageUtils<>(pageInfo.getRecords(), pageInfo.getTotal(), request.getPageSize()));
    }

    @ApiOperation("添加用户组织角色成员")
    @RequestMapping(value = "/addUserOrgRoleMember",headers="luxToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt addUserOrgRoleMember(@Validated @RequestBody AddUserOrgRoleMemberRequset request, BindingResult result) {
        userOrgRoleService.addUserOrgRoleMember(request);
        return CommonRlt.success();
    }


}
