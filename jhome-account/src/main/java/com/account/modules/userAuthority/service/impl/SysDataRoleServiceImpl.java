package com.account.modules.userAuthority.service.impl;

import com.account.common.sbUtil.domain.OperateLog;
import com.account.common.sbUtil.model.vo.OperateLogTypeEnum;
import com.account.modules.userAuthority.dao.RoleSysDataMapper;
import com.account.modules.userAuthority.dao.RoleSysMenuMapper;
import com.account.modules.userAuthority.dao.TenantMapper;
import com.account.modules.userAuthority.domain.RoleSysData;
import com.account.modules.userAuthority.domain.RoleSysMenu;
import com.account.modules.userAuthority.domain.Tenant;
import com.account.modules.userAuthority.model.request.AddRoleSysDataRequest;
import com.account.modules.userAuthority.model.request.CommonUserIdRequest;
import com.account.modules.userAuthority.model.response.GetSysDataResponse;
import com.account.modules.userAuthority.model.response.GetSysMenuResponse;
import com.account.modules.userAuthority.service.OperateLogService;
import com.account.modules.userAuthority.service.SysDataRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfvape.uid.UidGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDataRoleServiceImpl extends ServiceImpl<RoleSysDataMapper, RoleSysData> implements SysDataRoleService {

    @Autowired
    public UidGenerator uidGenerator;

    @Autowired
    public OperateLogService operateLogService;

    @Autowired
    public RoleSysDataMapper roleSysDataMapper;

    @Override
    public List<GetSysDataResponse> findRoleDataByUserId(CommonUserIdRequest request) {
        return  roleSysDataMapper.findRoleDataByUserId(request);
    }

    @Override
    public void addRoleSysData(AddRoleSysDataRequest request) {
        //先删除对应的角色菜单表数据
        LambdaUpdateWrapper<RoleSysData> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(RoleSysData::getRoleId, request.getRoleId());
        this.remove(lambdaUpdateWrapper);
        RoleSysData roleSysData;
        for (Long sysOrgId : request.getSysOrganizationIds()) {
            roleSysData = new RoleSysData();
            roleSysData.setId(uidGenerator.getUID());
            roleSysData.setRoleId(request.getRoleId());
            roleSysData.setSysOrganizationId(sysOrgId);
            this.save(roleSysData);
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("数据角色管理");
            operateLog.setSmallModule("分配数据角色菜单");
            operateLog.setTableName("role_sysData");
            operateLog.setType(OperateLogTypeEnum.INSERT);
            operateLog.setData1(roleSysData.getId() + "");
            operateLogService.saveOperateLog(operateLog);
        }
    }
    @Override
    public List<GetSysDataResponse> getUserRoleSysDataList(AddRoleSysDataRequest request) {
        LambdaQueryWrapper<RoleSysData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleSysData::getRoleId, request.getRoleId());
        return roleSysDataMapper.selectList(queryWrapper).stream().map(c -> {
            GetSysDataResponse getSysDataResponse = new GetSysDataResponse();
            BeanUtils.copyProperties(c, getSysDataResponse);
            return getSysDataResponse;
        }).collect(Collectors.toList());
    }
}
