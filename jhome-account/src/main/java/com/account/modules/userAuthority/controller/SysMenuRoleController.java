package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.common.sbUtil.PageUtils;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.GetRoleResponse;
import com.account.modules.userAuthority.model.response.GetRoleSysMenuResponse;
import com.account.modules.userAuthority.model.response.GetSysMenuResponse;
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
 * 菜单角色管理
 */
@Api(value = "菜单角色管理", tags = "Q、权限服务-菜单角色管理")
@RestController
@RequestMapping(value = "/menu", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class SysMenuRoleController   {

    @Autowired
    private SysMenuRoleService sysMenuRoleService;

    @ApiOperation("用户分配角色")
    @RequestMapping(value = "/role/addUserRole", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt addUserRole(@Validated @RequestBody AddUserOrgRoleRequest request, BindingResult result) {
        sysMenuRoleService.addUserRole(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }


    @ApiOperation("角色分配菜单")
    @RequestMapping(value = "/role/addRoleSysMenu", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt addRoleSysMenu(@Validated @RequestBody AddRoleSysMenuRequest request, BindingResult result) {
        sysMenuRoleService.addRoleSysMenu(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("添加或修改角色")
    @RequestMapping(value = "/role/addRole", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt addRole(@Validated @RequestBody AddRoleRequest request, BindingResult result) {
        sysMenuRoleService.addRole(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("修改角色")
    @RequestMapping(value = "/role/editRole", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt editRole(@Validated @RequestBody EditRoleRequest request, BindingResult result) {
        sysMenuRoleService.editRole(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("删除角色")
    @RequestMapping(value = "/role/deleteRole", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt deleteRole(@Validated @RequestBody CommonIdRequest request, BindingResult result) {
        sysMenuRoleService.deleteRole(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("添加或修改菜单")
    @RequestMapping(value = "/addOrUpdateMenu", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt addOrUpdateMenu(@Validated @RequestBody AddOrUpdateMenuRequest request, BindingResult result) {
        sysMenuRoleService.addOrUpdateMenu(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("删除菜单及子菜单")
    @RequestMapping(value = "/deleteMenu", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt deleteMenu(@Validated @RequestBody CommonProductAndTenantIdRequest request, BindingResult result) {
        sysMenuRoleService.deleteMenu(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("添加或修改按钮")
    @RequestMapping(value = "/addOrUpdateButton", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt addOrUpdateButton(@Validated @RequestBody AddOrUpdateButtonRequest request, BindingResult result) {
        sysMenuRoleService.addOrUpdateButton(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("获取用户所有菜单")
    @RequestMapping(value = "/list",headers="jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public  CommonRlt<List<GetSysMenuResponse>> listSysMenu(@Validated @RequestBody CommonUserIdRequest request, BindingResult result) {
        CommonRlt<List<GetSysMenuResponse>> commonRlt = new CommonRlt<>(BasicRestStatusEnum.OK, sysMenuRoleService.getSysMenuByUserId(request));
        //String reustJson=JSONUtils.beanToJson(commonRlt);
        return commonRlt;
    }

    @ApiOperation("获取用户所有角色")
    @RequestMapping(value = "/role/list", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt<PageUtils<List<GetRoleResponse>>> listRole(@Validated @RequestBody ListSysMenuRequest request, BindingResult result) {
        IPage<GetRoleResponse> pageInfo = sysMenuRoleService.listRole(request);
        return CommonRlt.success(new PageUtils<>(pageInfo.getRecords(), pageInfo.getTotal(), request.getPageSize()));
    }

    @ApiOperation("获取租户所有角色")
    @RequestMapping(value = "/role/listTenantRole", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt<List<GetRoleResponse>> listTenantRole(@Validated @RequestBody CommonTenantIdRequest request, BindingResult result) {
        return CommonRlt.success(sysMenuRoleService.listTenantRole(request.getTenantId()));
    }

    @ApiOperation("获取角色所有菜单叶子节点ID")
    @RequestMapping(value = "/role/listRoleMenu", headers = "jhomeToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonRlt<List<GetRoleSysMenuResponse>> listRoleMenu(@Validated @RequestBody CommonProductAndTenantIdRequest request, BindingResult result) {
        return new CommonRlt<>(BasicRestStatusEnum.OK, sysMenuRoleService.listRoleMenu(request));
    }
}
