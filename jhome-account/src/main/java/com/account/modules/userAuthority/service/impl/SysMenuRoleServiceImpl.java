package com.account.modules.userAuthority.service.impl;


import com.account.common.exception.AccountException;
import com.account.common.sbUtil.domain.OperateLog;
import com.account.common.sbUtil.model.vo.OperateLogTypeEnum;
import com.account.common.sbUtil.rest.HttpStatusCodesEnum;
import com.account.modules.userAuthority.dao.*;
import com.account.modules.userAuthority.domain.*;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.GetRoleResponse;
import com.account.modules.userAuthority.model.response.GetRoleSysMenuResponse;
import com.account.modules.userAuthority.model.response.GetSysMenuResponse;
import com.account.modules.userAuthority.service.OperateLogService;
import com.account.modules.userAuthority.service.SysMenuRoleService;
import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.util.DateUtils;
import com.ar.common.util.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.xfvape.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author daxv
 * @date 2019/11/26 15:27
 */
@Service
@Slf4j
public class SysMenuRoleServiceImpl implements SysMenuRoleService {

    @Autowired
    SysMenuRoleMapper sysMenuRoleMapper;
    @Autowired
    UserOrgRoleMapper userOrgRoleMapper;
    @Autowired
    UidGenerator uidGenerator;
    @Autowired
    SysMenuMapper sysMenuMapper;
    @Autowired
    RoleSysMenuMapper roleSysMenuMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    SysArgMapper sysArgMapper;
    @Autowired
    OperateLogService operateLogService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRole(AddUserOrgRoleRequest request) {
        //先删除对应的用户角色表数据
        LambdaUpdateWrapper<UserOrganizationRole> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(UserOrganizationRole::getUserId, request.getUserId());
        userOrgRoleMapper.delete(lambdaUpdateWrapper);
        UserOrganizationRole userOrganizationRole;
        for (Long roleId : request.getRoleIds()) {
            userOrganizationRole = new UserOrganizationRole();
            userOrganizationRole.setId(uidGenerator.getUID());
            userOrganizationRole.setUserId(request.getUserId());
            userOrganizationRole.setOrgId(request.getOrgId());
            userOrganizationRole.setRoleId(roleId);
            userOrganizationRole.setCreateTime(DateUtils.getyyyyMMddHHmmss2()); 
            userOrgRoleMapper.insert(userOrganizationRole);
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("菜单角色管理");
            operateLog.setSmallModule("新增用户角色");
            operateLog.setTableName("user_organization_role");
            operateLog.setType(OperateLogTypeEnum.INSERT);
            operateLog.setData1(userOrganizationRole.getId() + "");
            operateLogService.saveOperateLog(operateLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateMenu(AddOrUpdateMenuRequest request) {
        //判断是否菜单已存在
        LambdaQueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>().lambda();
        queryWrapper.eq(SysMenu::getName, request.getName())
                .eq(SysMenu::getTenantId, request.getTenantId())
                .eq(SysMenu::getDeleteFlag, 0);
        SysMenu curMenu = sysMenuMapper.selectOne(queryWrapper);
        if (curMenu != null) {
            if (request.getId() == null || !request.getId().equals(curMenu.getId())) {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.EXISTS_DATA_ALREADY, "菜单名称已存在");
                throw new AccountException("菜单名称已存在");
            }
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(request, sysMenu);
        sysMenu.setDeleteFlag(0);
        if (request.getId() == null) {
            sysMenu.setId(uidGenerator.getUID());
            sysMenuMapper.insert(sysMenu);
        } else {
            sysMenuMapper.updateById(sysMenu);
        }
        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("菜单角色管理");
        operateLog.setSmallModule("新增菜单");
        operateLog.setTableName("sys_menu");
        operateLog.setType(OperateLogTypeEnum.INSERT);
        operateLog.setData1(sysMenu.getId() + "");
        operateLogService.saveOperateLog(operateLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateButton(AddOrUpdateButtonRequest request) {
        //判断是否菜单已存在
        LambdaQueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>().lambda();
        queryWrapper.eq(SysMenu::getName, request.getName())
                .eq(SysMenu::getTenantId, request.getTenantId())
                .eq(SysMenu::getDeleteFlag, 0);
        SysMenu curMenu = sysMenuMapper.selectOne(queryWrapper);
        if (curMenu != null) {
            if (request.getId() == null || !request.getId().equals(curMenu.getId())) {
                throw new AccountException("菜单名称已存在");
            }
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(request, sysMenu);
        sysMenu.setDeleteFlag(0);
        if (request.getId() == null) {
            sysMenu.setId(uidGenerator.getUID());
            sysMenuMapper.insert(sysMenu);
        } else {
            sysMenuMapper.updateById(sysMenu);
        }
        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("菜单角色管理");
        operateLog.setSmallModule("新增按钮");
        operateLog.setTableName("sys_menu");
        operateLog.setType(OperateLogTypeEnum.INSERT);
        operateLog.setData1(sysMenu.getId() + "");
        operateLogService.saveOperateLog(operateLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRoleSysMenu(AddRoleSysMenuRequest request) {
        //先删除对应的角色菜单表数据
        LambdaUpdateWrapper<RoleSysMenu> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(RoleSysMenu::getRoleId, request.getRoleId());
        roleSysMenuMapper.delete(lambdaUpdateWrapper);
        RoleSysMenu roleSysMenu;
        for (Long sysMenuId : request.getSysMenuIds()) {
            roleSysMenu = new RoleSysMenu();
            roleSysMenu.setId(uidGenerator.getUID());
            roleSysMenu.setRoleId(request.getRoleId());
            roleSysMenu.setSysMenuId(sysMenuId);
            roleSysMenuMapper.insert(roleSysMenu);
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("菜单角色管理");
            operateLog.setSmallModule("新增角色菜单");
            operateLog.setTableName("role_sysmenu");
            operateLog.setType(OperateLogTypeEnum.INSERT);
            operateLog.setData1(roleSysMenu.getId() + "");
            operateLogService.saveOperateLog(operateLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole(AddRoleRequest request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        role.setId(uidGenerator.getUID());
        role.setCreateTime(DateUtils.getyyyyMMddHHmmss2());
        role.setDeleteFlag(0);
        roleMapper.insert(role);
        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("菜单角色管理");
        operateLog.setSmallModule("新增角色");
        operateLog.setTableName("role");
        operateLog.setUserId(request.getCreateUserId());
        operateLog.setType(OperateLogTypeEnum.INSERT);
        operateLog.setData1(role.getId() + "");
        operateLogService.saveOperateLog(operateLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editRole(EditRoleRequest request) {
        Role role = roleMapper.selectById(request.getId());
        if (role != null) {
            role.setRoleName(request.getRoleName());
            role.setRemark(request.getRemark());
            role.setUpdateUserId(request.getUpdateUserId());
            role.setUpdateTime(DateUtils.getyyyyMMddHHmmss2());
            roleMapper.updateById(role);
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("菜单角色管理");
            operateLog.setSmallModule("编辑角色");
            operateLog.setTableName("role");
            operateLog.setUserId(request.getUpdateUserId());
            operateLog.setType(OperateLogTypeEnum.UPDATE);
            operateLog.setData1(role.getId() + "");
            operateLog.setData2(role.getRoleName());
            operateLog.setData3(role.getRemark());
            operateLogService.saveOperateLog(operateLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(CommonIdRequest request) {
        Role role;
        //是否角色被用户引用
        LambdaQueryWrapper<UserOrganizationRole> queryWrapper = new QueryWrapper<UserOrganizationRole>().lambda();
        queryWrapper.eq(UserOrganizationRole::getRoleId, request.getId());
        if (userOrgRoleMapper.selectOne(queryWrapper) == null) {
            LambdaQueryWrapper<RoleSysMenu> queryWrapper2 = new QueryWrapper<RoleSysMenu>().lambda();
            queryWrapper2.eq(RoleSysMenu::getRoleId, request.getId());
            if (roleSysMenuMapper.selectOne(queryWrapper2) == null) {
                role = new Role();
                role.setId(request.getId());
                role.setDeleteFlag(1);
                roleMapper.updateById(role);
                OperateLog operateLog = new OperateLog();
                operateLog.setBigModule("菜单角色管理");
                operateLog.setSmallModule("删除角色");
                operateLog.setTableName("role");
                operateLog.setType(OperateLogTypeEnum.DELETE);
                operateLog.setData1(request.getId() + "");
                operateLogService.saveOperateLog(operateLog);
            } else {
                throw new AccountException("角色已分配菜单，不能删除");
            }
        } else {
            //Shift.fatalOnlyDetail(BasicRestStatusEnum.DATA_WAS_RELATED, "角色已被用户使用，不能删除");
            throw new AccountException("角色已被用户使用，不能删除");
        }
    }

    @Override
    public IPage<GetRoleResponse> listRole(ListSysMenuRequest request) {
        IPage<GetRoleResponse> page = new Page<GetRoleResponse>(request.getPageNum(), request.getPageSize());
        //超级管理员
        if (userOrgRoleMapper.findSuperUserId(request.getUserId()) != null) {
            page = sysMenuRoleMapper.findAllRole(page);
        } else {
            page = sysMenuRoleMapper.findRoleByTenantId(page, request.getTenantId());
        }
        return page;
    }

    @Override
    public List<GetSysMenuResponse> getSysMenuByUserId(CommonUserIdRequest request) {
        List<GetSysMenuResponse> list = null;
        //超级管理员
        if (userOrgRoleMapper.findSuperUserId(request.getUserId()) != null) {
            list = sysMenuRoleMapper.findAllMenu(request.getTenantId());
        } else {
            list = sysMenuRoleMapper.findMenuByUserId(request.getUserId(), request.getTenantId());
        }
        list = sortTree(list, 0L);
        //设置showChild
        for (GetSysMenuResponse getSysMenuResponse : list) {
            List<GetSysMenuResponse> childrenList = getSysMenuResponse.getChildren();
            if (childrenList != null) {
                for (GetSysMenuResponse children : childrenList) {
                    if (!children.getHidden()) {
                        getSysMenuResponse.setShowChild((short) 1);
                    }
                }
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(CommonProductAndTenantIdRequest request) {
        //是否被角色引用
        LambdaQueryWrapper<RoleSysMenu> queryWrapper = new QueryWrapper<RoleSysMenu>().lambda();
        queryWrapper.eq(RoleSysMenu::getId, request.getId());
        if (roleSysMenuMapper.selectOne(queryWrapper) == null) {
            //是否已有子菜单
            List<Long> list = sysMenuMapper.findSubMenuIdByProductIdAndTenantId(request.getId(), request.getTenantId());
            if (list == null || list.size() == 0) {
                SysMenu sysMenu = new SysMenu();
                sysMenu.setId(request.getId());
                sysMenu.setDeleteFlag(1);
                sysMenuMapper.updateById(sysMenu);
                OperateLog operateLog = new OperateLog();
                operateLog.setBigModule("菜单角色管理");
                operateLog.setSmallModule("删除菜单");
                operateLog.setTableName("sys_menu");
                operateLog.setType(OperateLogTypeEnum.DELETE);
                operateLog.setData1(request.getId() + "");
                operateLogService.saveOperateLog(operateLog);
            } else {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.DATA_WAS_RELATED, "菜单已有子菜单，不能删除");
                throw new AccountException("菜单已有子菜单，不能删除");
            }
        } else {
            //Shift.fatalOnlyDetail(BasicRestStatusEnum.DATA_WAS_RELATED, "菜单已被角色使用，不能删除");
            throw new AccountException("菜单已被角色使用，不能删除");
        }
    }

    @Override
    public List<GetRoleSysMenuResponse> listRoleMenu(CommonProductAndTenantIdRequest request) {
        List<GetRoleSysMenuResponse> list = null;
        //超级管理员
        if (request.getId().equals(sysArgMapper.findSuperRoleId())) {
            list = sysMenuRoleMapper.findAllRoleLeafMenu(request.getTenantId());
        } else {
            list = sysMenuRoleMapper.findLeafMenuByRoleId(request.getId(), request.getTenantId());
        }
        return list;
    }

    private List<GetSysMenuResponse> sortTree(List<GetSysMenuResponse> list, Long parentId) {//递归塞值
        List<GetSysMenuResponse> returnList = new ArrayList<>();
        for (GetSysMenuResponse menu : list) {
            if (parentId.equals(menu.getParentId())) {
                menu.setChildren(sortTree(list, menu.getId()));
                returnList.add(menu);
            }
        }
        return returnList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSysDefaultRole(Long createUserId, Long tenantId, String tenantName) {
        List<Long> roleIds = Lists.newArrayList();
        Long teacherRoleId, managerRoleId;
        Role role = new Role();
        //添加默认学生角色
        role.setRoleName("学生");
        role.setRemark(tenantName+"学生角色");
        role.setId(uidGenerator.getUID());
        role.setCreateUserId(createUserId);
        role.setTenantId(tenantId);
        role.setCreateTime(DateUtils.getyyyyMMddHHmmss2());
        role.setDeleteFlag(0);
        roleIds.add(role.getId());
        roleMapper.insert(role);
        //添加默认教师角色
        role.setRoleName("教师");
        role.setRemark(tenantName+"教师角色");
        teacherRoleId = uidGenerator.getUID();
        role.setId(teacherRoleId);
        roleIds.add(role.getId());
        roleMapper.insert(role);
        //添加默认管理员角色
        role.setRoleName("系统管理员");
        role.setRemark(tenantName+"管理员角色");
        managerRoleId = uidGenerator.getUID();
        role.setId(managerRoleId);
        roleIds.add(role.getId());
        roleMapper.insert(role);
        //添加默认的角色菜单
        addDefaultRoleSysMenu(teacherRoleId, managerRoleId);
        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("菜单角色管理");
        operateLog.setSmallModule("新增角色");
        operateLog.setTableName("role");
        operateLog.setUserId(createUserId);
        operateLog.setType(OperateLogTypeEnum.INSERT);
        operateLog.setData1(JsonUtils.parse(roleIds));
        operateLogService.saveOperateLog(operateLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addDefaultRoleSysMenu(Long teacherRoleId, Long managerRoleId) {
        List<SysArg> sysArgList = sysArgMapper.selectList(Wrappers.emptyWrapper());
        if (sysArgList.size() == 0) {
            //Shift.fatalOnlyDetail(HttpStatusCodesEnum.ERROR_SMS_FAIL, "系统参数错误");
            throw new AccountException("系统参数错误");
        }
        //添加默认教师角色菜单
        LambdaQueryWrapper<RoleSysMenu> queryWrapper = new QueryWrapper<RoleSysMenu>().lambda();
        queryWrapper.eq(RoleSysMenu::getRoleId, sysArgList.get(0).getTeacherDefaultRoleId());
        List<RoleSysMenu> teacherList = roleSysMenuMapper.selectList(queryWrapper);
        RoleSysMenu newRoleSysMenu;
        for (RoleSysMenu roleSysMenu : teacherList) {
            newRoleSysMenu = new RoleSysMenu();
            newRoleSysMenu.setId(uidGenerator.getUID());
            newRoleSysMenu.setRoleId(teacherRoleId);
            newRoleSysMenu.setSysMenuId(roleSysMenu.getSysMenuId());
            roleSysMenuMapper.insert(newRoleSysMenu);
        }
        //添加默认管理员角色菜单
        queryWrapper = new QueryWrapper<RoleSysMenu>().lambda();
        queryWrapper.eq(RoleSysMenu::getRoleId, sysArgList.get(0).getManagerDefaultRoleId());
        List<RoleSysMenu> managerList = roleSysMenuMapper.selectList(queryWrapper);
        for (RoleSysMenu roleSysMenu : managerList) {
            newRoleSysMenu = new RoleSysMenu();
            newRoleSysMenu.setId(uidGenerator.getUID());
            newRoleSysMenu.setRoleId(managerRoleId);
            newRoleSysMenu.setSysMenuId(roleSysMenu.getSysMenuId());
            roleSysMenuMapper.insert(newRoleSysMenu);
        }
    }

    @Override
    public List<GetRoleResponse> listTenantRole(Long tenantId) {
        return sysMenuRoleMapper.findRoleByTenantId(tenantId);
    }

}
