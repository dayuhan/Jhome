package com.account.modules.userAuthority.service.impl;

import com.account.common.CommonRlt;
import com.account.common.sbUtil.Shift;
import com.account.common.sbUtil.domain.OperateLog;
import com.account.common.sbUtil.model.vo.OperateLogTypeEnum;
import com.account.modules.userAuthority.dao.OrganizationMapper;
import com.account.modules.userAuthority.dao.RoleMapper;
//import com.account.modules.userAuthority.dao.TenantMapper;
import com.account.modules.userAuthority.dao.UserOrgRoleMapper;
import com.account.modules.userAuthority.domain.Organization;
import com.account.modules.userAuthority.domain.Tenant;
import com.account.modules.userAuthority.domain.UserOrganizationRole;
import com.account.modules.userAuthority.model.dto.OrganizationDTO;
import com.account.modules.userAuthority.model.dto.UserOrgDTO;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.ListOrgUserNumRes;
import com.account.modules.userAuthority.model.response.ListOrganizationRes;
import com.account.modules.userAuthority.model.response.OrganizationDetailsRes;
import com.account.modules.userAuthority.service.OperateLogService;
import com.account.modules.userAuthority.service.OrganizationService;
import com.account.modules.userAuthority.service.SysMenuRoleService;
import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.util.DateUtils;
import com.ar.common.util.JsonUtils;
import com.ar.common.util.RandomStringUtils;
import com.ar.common.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfvape.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Slf4j
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UidGenerator uidGenerator;

    @Autowired
    private UserOrgRoleMapper userOrgRoleMapper;

    @Autowired
    OperateLogService operateLogService;

   // @Autowired
   //TenantMapper tenantMapper;

    @Autowired
    SysMenuRoleService sysMenuRoleService;

    @Autowired
    RoleMapper roleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonRlt addOrganization(AddOrganizationReq request) {
        Organization org = new Organization();
        //Code生成/校验(不重复Code)
        String code = returnCode();
        org.setId(uidGenerator.getUID());
        org.setOrganizationCode(code);
        //组织名称[顶级名称唯一,同级不重复]
        String name = request.getName();
        Long parentId = request.getParentId();
        checkName(name, parentId);
        org.setName(request.getName());
        org.setOrganizationCode(code);
        org.setSort(request.getSort());
        org.setLevel(request.getLevel());
        org.setParentId(request.getParentId());
        org.setType(request.getType());
        org.setIsLeaf(request.getIsLeaf());
        org.setCreateTime(DateUtils.getyyyyMMddHHmmss());
        org.setCreateUserId(request.getCreateUserId());
        org.setUpdateTime(DateUtils.getyyyyMMddHHmmss());
        org.setUpdateUserId(request.getCreateUserId());
        org.setDeleteFlag(0);
        org.setTenantId(request.getTenantId());
        if (parentId == 0) {
            org.setTenantId(org.getId());
        }
        this.save(org);
        if (parentId == 0) {
            Tenant tenant = new Tenant();
            tenant.setId(org.getId());
            tenant.setTenantName(request.getName());
            tenant.setCreateUserId(request.getCreateUserId());
            tenant.setDeleteFlag(0);
            tenant.setCreateTime(DateUtils.getyyyyMMddHHmmss());
            //tenantMapper.insert(tenant);
            //添加默认的学生、教师、管理员角色
            sysMenuRoleService.addSysDefaultRole(request.getCreateUserId(), org.getId(), request.getName());
        }
        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("组织管理");
        operateLog.setSmallModule("添加组织");
        operateLog.setTableName("organization");
        operateLog.setType(OperateLogTypeEnum.INSERT);
        operateLog.setData1(org.getId() + "");
        operateLogService.saveOperateLog(operateLog);
        return new CommonRlt(BasicRestStatusEnum.OK);
    }

    @Override
    public IPage<ListOrganizationRes> findOrganizationList(ListOrganizationReq request) {
        IPage<ListOrganizationRes> page = new Page<>(request.getPageNum(), request.getPageSize());
        Organization org = new Organization();
        org.setParentId((long) 0);
        //根目录集合
        //超级管理员，查询所有有效数据
        if(!request.getRoleName().contains("超级管理员")) {
            if(request.getRoleName().contains("系统管理员")) {
                //系统管理员,查询tenantId下的所有有效数据
                org.setTenantId(request.getTenantId());
            } else  {
                org.setName(request.getName());
                org.setOrganizationCode(request.getOrganizationCode());
                org.setTenantId(request.getTenantId());
            }
        }
        //系统管理员,查询参数下的所有有效数据
        IPage<ListOrganizationRes> listOrganizationResIPage = organizationMapper.findOrganizationList(page, org);
        if (listOrganizationResIPage.getRecords() == null || listOrganizationResIPage.getRecords().size() == 0) {
            log.error(BasicRestStatusEnum.NULL_NO_DATA.subMessage());
            Shift.fatal(BasicRestStatusEnum.NULL_NO_DATA);
        }
        for (ListOrganizationRes orgList : listOrganizationResIPage.getRecords()) {
            //主键
            Long id = orgList.getId();
            //从级递归
            List<ListOrganizationRes> childList = organizationMapper.findByChildrenList(id);
            orgList.setChildren(childList);
            buildChildInfo(orgList);
        }
        return listOrganizationResIPage;
    }

    @Override
    public CommonRlt editOrganization(EditOrganizationReq request) {
        Organization org = new Organization();
        String name = request.getName();
        Long parentId = request.getParentId();
        checkName(name, parentId);
        org.setName(request.getName());
        Integer count = organizationMapper.updateByOrg(request);
        //同步更新tenant表的名称信息
        if (parentId == 0) {
           // Tenant tenant = tenantMapper.selectById(org.getTenantId());
            //tenant.setTenantName(org.getName());
            //tenantMapper.updateById(tenant);
        }

        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("组织管理");
        operateLog.setSmallModule("修改组织");
        operateLog.setTableName("organization");
        operateLog.setType(OperateLogTypeEnum.UPDATE);
        operateLog.setData1(org.getId() + "");
        operateLog.setData1(org.getName() + "");
        operateLogService.saveOperateLog(operateLog);
        if (count > 0) {
            return new CommonRlt(BasicRestStatusEnum.OK);
        }
        return new CommonRlt(BasicRestStatusEnum.FAIL);
    }

    @Override
    public CommonRlt batchDelete(CommonIdsRequest request) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setIds(request.getIds());
        dto.setDeleteFlag(1);
        Integer count = organizationMapper.updateByIds(dto);
        //添加操作日志
        for (Long userId : request.getIds()) {
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("组织管理");
            operateLog.setSmallModule("删除组织");
            operateLog.setTableName("organization");
            operateLog.setRemarks(StringUtil.truncationStr(JsonUtils.parse(dto), 200));
            operateLog.setType(OperateLogTypeEnum.DELETE);
            operateLog.setData1(userId + "");
            operateLogService.saveOperateLog(operateLog);
        }
        if (count > 0) {
            return new CommonRlt(BasicRestStatusEnum.OK);
        }
        return new CommonRlt(BasicRestStatusEnum.FAIL);
    }

    @Override
    public CommonRlt deleteUserOrg(CommonIdRequest request) {
        UserOrganizationRole userOrganizationRole = userOrgRoleMapper.selectById(request.getId());
        if (StringUtil.isEmpty(userOrganizationRole)) {
            log.error(BasicRestStatusEnum.NOT_EXISTS_DATA.subMessage());
            Shift.fatal(BasicRestStatusEnum.NOT_EXISTS_DATA, "参数ID错误");
        }
        Integer count = organizationMapper.deleteUserOrg(request.getId());
        if (count > 0) {
            return new CommonRlt(BasicRestStatusEnum.OK);
        }
        return new CommonRlt(BasicRestStatusEnum.FAIL);
    }

    @Override
    public List<ListOrgUserNumRes> findOrgUserNum(OrgUserRoleNum req) {
        List<ListOrgUserNumRes> list = organizationMapper.findOrgUserNum(req);
        if(StringUtil.isEmpty(list)){
            Shift.fatal(BasicRestStatusEnum.NULL_NO_DATA);
        }
        for(ListOrgUserNumRes listOrg : list){
            LambdaQueryWrapper<UserOrganizationRole> queryWrapper = new QueryWrapper<UserOrganizationRole>().lambda();
            queryWrapper.eq(UserOrganizationRole::getOrgId,listOrg.getId());

            if(req.getRoleId() != null){
                queryWrapper.eq(UserOrganizationRole::getOrgId,listOrg.getId());
            }
            int count = userOrgRoleMapper.selectCount(queryWrapper);
            listOrg.setUserNum(count);
        }
        return list;
    }

    @Override
    public CommonRlt findOrgDetails(Long id) {
        OrganizationDetailsRes organizationDetailsRes = new OrganizationDetailsRes();
        Organization orgList = organizationMapper.findOrgDetails(id);
        if (StringUtil.isEmpty(orgList)) {
            log.error(BasicRestStatusEnum.NULL_NO_DATA.subMessage());
            Shift.fatal(BasicRestStatusEnum.NULL_NO_DATA);
        }
        Long parentId;
        parentId = orgList.getParentId();
        organizationDetailsRes.setId(orgList.getId());
        organizationDetailsRes.setName(orgList.getName());
        organizationDetailsRes.setCreateTime(orgList.getCreateTime());
        organizationDetailsRes.setIsLeaf(orgList.getIsLeaf());
        organizationDetailsRes.setLevel(orgList.getLevel());
        organizationDetailsRes.setOrganizationCode(orgList.getOrganizationCode());
        organizationDetailsRes.setParentId(parentId);
        organizationDetailsRes.setSort(orgList.getSort());
        organizationDetailsRes.setTenantId(orgList.getTenantId());
        organizationDetailsRes.setType(orgList.getType());
        organizationDetailsRes.setUpdateTime(orgList.getUpdateTime());
        if (parentId != 0) {
            //父级ID 查询上级信息
            Organization parentDetails = organizationMapper.findOrgDetails(parentId);
            organizationDetailsRes.setParentName(parentDetails.getName());
        }
        return new CommonRlt(BasicRestStatusEnum.OK, organizationDetailsRes);
    }

    public List<UserOrgDTO> buildOrgInfo(Long parentId, List<UserOrgDTO> listUserOrgRes) {
        Organization orgApp = organizationMapper.findAppOrgList(parentId);
        //构建用户组织信息
        if (StringUtil.isNotEmpty(orgApp)) {
            UserOrgDTO userOrgDTO = new UserOrgDTO();
            userOrgDTO.setId(orgApp.getId());
            userOrgDTO.setOrgName(orgApp.getName());
            userOrgDTO.setLevel(orgApp.getLevel());
            //添加集合信息
            listUserOrgRes.add(userOrgDTO);
            buildOrgInfo(orgApp.getParentId(), listUserOrgRes);
        }
        return listUserOrgRes;
    }

    public ListOrganizationRes buildChildInfo(ListOrganizationRes orgList) {
        List<ListOrganizationRes> childLists = orgList.getChildren();
        //子集后无限层
        for (ListOrganizationRes childOrgList : childLists) {
            Long id = childOrgList.getId();
            List<ListOrganizationRes> list = organizationMapper.findByChildrenList(id);
            if (StringUtil.isNotEmpty(list)) {
                childOrgList.setChildren(list);
                buildChildInfo(childOrgList);
            }
        }
        return orgList;
    }

    /**
     * 不重复 组织Code
     *
     * @return
     */
    public String returnCode() {
        QueryWrapper<Organization> queryWrapper;
        String code;
        while (true) {
            queryWrapper = new QueryWrapper<>();
            code = RandomStringUtils.getRandomString(6);
            queryWrapper.lambda().eq(Organization::getOrganizationCode, code);
            List<Organization> listOrganizationRes = organizationMapper.selectList(queryWrapper);
            if (listOrganizationRes == null || listOrganizationRes.size() == 0) {
                return code;
            }
        }
    }

    /**
     * 校验
     * 顶级名称不重复
     * 同顶级 从级Name不重复(不同顶级的从级支持Name重复)
     *
     * @return
     */
    public String checkName(String name, Long parentId) {
        Organization organization = new Organization();
        organization.setParentId(parentId);
        List<ListOrganizationRes> org = organizationMapper.findByOrgList(organization);
        for (ListOrganizationRes orgRes : org) {
            String orgResName = orgRes.getName();
            if (name.equals(orgResName)) {
                log.error("checkName is exists info:" + orgRes.getName());
                Shift.fatal(BasicRestStatusEnum.DATA_WAS_NAME, orgRes.getName());
            }
        }
        return null;
    }
}
