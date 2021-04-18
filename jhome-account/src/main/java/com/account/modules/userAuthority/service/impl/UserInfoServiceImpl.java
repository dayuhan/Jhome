package com.account.modules.userAuthority.service.impl;

import com.account.common.CommonRlt;
import com.account.common.exception.AccountException;
import com.account.common.sbUtil.Shift;
import com.account.common.sbUtil.domain.OperateLog;
import com.account.common.sbUtil.model.vo.OperateLogTypeEnum;
import com.account.common.sbUtil.rest.HttpStatusCodesEnum;
import com.account.common.sbUtil.util.ReadExcelUtils;
import com.account.common.utils.UserAuxiliary;
import com.account.modules.userAuthority.dao.*;
import com.account.modules.userAuthority.domain.*;
import com.account.modules.userAuthority.model.dto.*;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.*;
import com.account.modules.userAuthority.service.*;
import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.util.DateUtils;
import com.ar.common.util.RSAEncryptUtils;
import com.ar.common.util.RandomStringUtils;
import com.ar.common.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfvape.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ar.common.util.Constant.USER_AGENT;


@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, User> implements UserInfoService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserAuxiliary userAuxiliary;
    @Autowired
    SysArgMapper sysArgMapper;
    @Autowired
    LoginLogService loginLogService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UidGenerator uidGenerator;
    @Autowired
    UserOrgRoleMapper userOrgRoleMapper;
    @Autowired
    UserOrgRoleService userOrgRoleService;
    @Autowired
    OperateLogService operateLogService;
    @Autowired
    OrganizationMapper organizationMapper;

    @Autowired
    SmsRecordMapper smsRecordMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    TokenService tokenService;

    private final int SECONDS = 600000;

    private final String PASSWORD = "123456";

    @Value("${rsa.private-key}")
    private String privateKey;

    @Override
    public UcTokenRes loginByAccount(LoginAccountReq request, HttpServletRequest httpRequest) {
        List<SysArg> sysArg = sysArgMapper.selectList(Wrappers.emptyWrapper());
        if (sysArg.size() == 0) {
            //Shift.fatalOnlyDetail(HttpStatusCodesEnum.ERROR_SMS_FAIL, "系统参数错误");
            throw new AccountException("系统参数错误");
        }
        try {
            List<LoginRes> list = userInfoMapper.findByLoginName(request.getLoginName());
            if (list == null || list.size() == 0) {
                Shift.fatal(BasicRestStatusEnum.NOT_EXISTS_USER);
            } else if (list.size() > 1) {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.USER_ACCOUNT_ERR, "用户名出现重复");
                throw new AccountException("用户名出现重复");
            }
            LoginRes loginRes = list.get(0);
            if (loginRes.getStatus() == 2) {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.INVALID_USER_DISABLED, "账号已被禁用");
                throw new AccountException("账号已被禁用");
            }
            String pwd = RSAEncryptUtils.decrypt(request.getPwd(), privateKey);
            if (!loginRes.getPassword().equals(StringUtil.hashPassword(pwd, loginRes.getSalt()))) {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.INVALID_SUBJECT_CREDENTIALS, "用户名或密码不正确");
                throw new AccountException("用户名或密码不正确");
            }

            if (request.getNetType() != null && request.getNetType() == 1) {
                loginRes.setDfsServer(sysArg.get(0).getDfsServerLocal());
                loginRes.setDfsUploadServer(sysArg.get(0).getUploadServerLocal());
            } else {
                if (request.getLoginDevice() != null && request.getLoginDevice() == 1) {
                    loginRes.setDfsServer(sysArg.get(0).getReserve1());
                } else {
                    loginRes.setDfsServer(sysArg.get(0).getDfsServer());
                }
                loginRes.setDfsUploadServer(sysArg.get(0).getUploadServer());
            }
            loginRes.setSmsServer(sysArg.get(0).getSmsServer());
            if (StringUtil.isNotEmpty(list.get(0).getTenantId())) {
                Organization org = organizationMapper.selectById(list.get(0).getTenantId());
                loginRes.setSchoolName(org.getName());
            }
            List<UserOrgRoleDTO> userOrgRoleDTOList = userOrgRoleService.listUserOrgRoleByUserId(Long.valueOf(loginRes.getUserId()), loginRes.getTenantId());
            loginRes.setUserOrgRoleList(userOrgRoleDTOList);
            loginLogService.loginIn(loginRes.getUserId(), request.getLoginIp(), request.getLoginDevice(), request.getLoginSource(), request.getEquipmentType(), loginRes.getTenantId());

            String token = tokenService.generateToken(
                    httpRequest.getHeader(USER_AGENT), loginRes);
            tokenService.save(token, loginRes);
            UcTokenRes ucTokenRes = new UcTokenRes(token,
                    Calendar.getInstance().getTimeInMillis() + TokenService.SESSION_TIMEOUT * 1000,
                    Calendar.getInstance().getTimeInMillis(), loginRes);
            return ucTokenRes;
        } catch (RestClientException e) {
            logger.error("userInfoImpl request err:", e);
            //Shift.fatalOnlyDetail(HttpStatusCodesEnum.ERROR_SMS_FAIL, e.getMessage());
            throw new AccountException(e.getMessage());
        }

    }

    @Override
    public UcTokenRes loginByCode(LoginCodeReq request, HttpServletRequest httpRequest) {
        List<SysArg> sysArg = sysArgMapper.selectList(Wrappers.emptyWrapper());
        if (sysArg.size() == 0) {
            //Shift.fatalOnlyDetail(HttpStatusCodesEnum.ERROR_SMS_FAIL, "系统参数错误");
            throw new AccountException("系统参数错误");

        }
        try {
            List<LoginRes> list = userInfoMapper.findByLoginName(request.getMobile());
            if (list == null || list.size() == 0) {
                Shift.fatal(BasicRestStatusEnum.NOT_EXISTS_USER);
            } else if (list.size() > 1) {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.USER_ACCOUNT_ERR, "手机号出现重复");
                throw new AccountException("手机号出现重复");
            }
            LoginRes loginRes = list.get(0);
            if (loginRes.getStatus() == 2) {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.INVALID_USER_DISABLED, "账号已被禁用");
                throw new AccountException("账号已被禁用");
            }
            String pwd = RSAEncryptUtils.decrypt(request.getPwd(), privateKey);
            if (!loginRes.getPassword().equals(StringUtil.hashPassword(pwd, loginRes.getSalt()))) {
                //Shift.fatalOnlyDetail(BasicRestStatusEnum.INVALID_SUBJECT_CREDENTIALS, "用户名或密码不正确");
                throw new AccountException("用户名或密码不正确");
            }

            if (request.getNetType() != null && request.getNetType() == 1) {
                //dfsServer for Ios
                loginRes.setDfsServer(sysArg.get(0).getDfsServerLocal());
                loginRes.setDfsUploadServer(sysArg.get(0).getUploadServerLocal());
            } else {
                //dfsServer for Ios
                if (request.getLoginDevice() != null && request.getLoginDevice() == 1) {
                    loginRes.setDfsServer(sysArg.get(0).getReserve1());
                } else {
                    loginRes.setDfsServer(sysArg.get(0).getDfsServer());
                }
                loginRes.setDfsUploadServer(sysArg.get(0).getUploadServer());
            }
            loginRes.setSmsServer(sysArg.get(0).getSmsServer());
            if (StringUtil.isNotEmpty(list.get(0).getTenantId())) {
                Organization org = organizationMapper.selectById(list.get(0).getTenantId());
                loginRes.setSchoolName(org.getName());
            }
            List<UserOrgRoleDTO> userOrgRoleDTOList = userOrgRoleService.listUserOrgRoleByUserId(Long.valueOf(loginRes.getUserId()), loginRes.getTenantId());
            loginRes.setUserOrgRoleList(userOrgRoleDTOList);
            loginLogService.loginIn(loginRes.getUserId(), request.getLoginIp(), request.getLoginDevice(), request.getLoginSource(), request.getEquipmentType(), loginRes.getTenantId());
            //token
            String token = tokenService.generateToken(
                    httpRequest.getHeader(USER_AGENT), loginRes);
            tokenService.save(token, loginRes);
            UcTokenRes ucTokenRes = new UcTokenRes(token,
                    Calendar.getInstance().getTimeInMillis() + TokenService.SESSION_TIMEOUT * 1000,
                    Calendar.getInstance().getTimeInMillis(), loginRes);
            return ucTokenRes;
        } catch (RestClientException e) {
            logger.error("sms request err:", e);
            Shift.fatalOnlyDetail(HttpStatusCodesEnum.ERROR_SMS_FAIL, e.getMessage());
        }
        return null;
    }

    @Override
    public CommonRlt loginOut(HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        if (!tokenService.validate(request.getHeader(USER_AGENT), token)) {
            return new CommonRlt(BasicRestStatusEnum.AUTH_TOKEN_INVALID);
        }
        try {
            tokenService.delete(token);
            return new CommonRlt(BasicRestStatusEnum.REVOKE_TOKEN_SUCCESS, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonRlt(BasicRestStatusEnum.REVOKE_TOKEN_FAIL, "失败");
        }
    }

    @Transactional
    @Override
    public CommonRlt switchStatus(EditUserStatusReq request) {
        EditUserDto user = new EditUserDto();
        user.setIds(request.getIds());
        user.setStatus(request.getStatus());
        Integer count = userInfoMapper.updateByIds(user);
        //添加操作日志
        for (Long userId : request.getIds()) {
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("用户管理");
            operateLog.setSmallModule("修改状态");
            operateLog.setTableName("user");
            operateLog.setType(OperateLogTypeEnum.UPDATE);
            operateLog.setData1(userId + "");
            operateLog.setData1(request.getStatus() + "");
            operateLogService.saveOperateLog(operateLog);
        }
        if (count > 0) {
            return new CommonRlt(BasicRestStatusEnum.OK);
        }
        return new CommonRlt(BasicRestStatusEnum.FAIL);
    }

    @Transactional
    @Override
    public CommonRlt batchDelete(CommonIdsRequest request) {
        EditUserDto user = new EditUserDto();
        user.setIds(request.getIds());
        user.setDeleteFlag(1);
        Integer count = userInfoMapper.updateByIds(user);
        //添加操作日志
        for (Long userId : request.getIds()) {
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("用户管理");
            operateLog.setSmallModule("删除用户");
            operateLog.setTableName("user");
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
    public IPage<ListUserInfoRes> findUserList(ListUserReq request) {
        IPage<ListUserInfoRes> page = new Page<>(request.getPageNum(), request.getPageSize());
        //String rolenames = userOrgRoleService.getRoleNameByUserIdAndTenantId(request.getUserId(),request.getTenantId());
        if (request.getRoleName() != null && request.getRoleName().contains("超级管理员")) {
            request.setTenantId(null);
        }
        IPage<ListUserInfoRes> listUserInfoResIPage = userInfoMapper.findByUserList(page, request);
        if (listUserInfoResIPage.getTotal() == 0) {
            log.error(BasicRestStatusEnum.NULL_NO_DATA.subMessage());
            Shift.fatal(BasicRestStatusEnum.NULL_NO_DATA);
        }
        return listUserInfoResIPage;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonRlt getUserDetails(Long id) {
        List<UserDetailsRoleDTO> detailsRoleDtoList = new ArrayList<>();
        UserInfoBackstageRes user = userInfoMapper.getUserDetails(id);
        if (StringUtil.isEmpty(user)) {
            log.error(BasicRestStatusEnum.NULL_NO_DATA.subMessage());
            Shift.fatal(BasicRestStatusEnum.NULL_NO_DATA);
        }
        //用户组织角色关联信息
        List<UserOrganizationRole> userOrganizationRoleList = userInfoMapper.findUserOrgRoleList(id);
        UserDetailsRoleDTO userDetailsRoleDTO;
        RoleListDTO userRole = null;
        UserDetailsRoleDTO userOrg = null;
        userOrganizationRoleList = userOrganizationRoleList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()
                -> new TreeSet<>(Comparator.comparing(UserOrganizationRole::getOrgId))), ArrayList::new));
        if (StringUtil.isNotEmpty(userOrganizationRoleList)) {
            for (UserOrganizationRole userOrganizationRole : userOrganizationRoleList) {
                userDetailsRoleDTO = new UserDetailsRoleDTO();
                List<RoleListDTO> roleList = new ArrayList<>();
                Long orgId = userOrganizationRole.getOrgId();
                if (StringUtil.isNotEmpty(userOrganizationRole.getOrgId())) {
                    //组织信息
                    userOrg = userInfoMapper.getOrgInfo(orgId);
                    if (StringUtil.isNotEmpty(userOrg)) {
                        userDetailsRoleDTO.setName(userOrg.getName());
                        userDetailsRoleDTO.setOrganizationCode(userOrg.getOrganizationCode());
                        userDetailsRoleDTO.setOrgId(orgId == null ? 0 : orgId);
                        userDetailsRoleDTO.setTenantId(userOrg.getTenantId());
                    }
                    //组织可能为空,根据UserId关联当前自己为空的组织
                    List<UserOrganizationRole> roleIds = userInfoMapper.findUserOrgRoleIdList(orgId, id);
                    //角色信息
                    for (UserOrganizationRole ids : roleIds) {
                        Long roleId = ids.getRoleId();
                        userRole = userInfoMapper.getRoleInfo(roleId);
                        userDetailsRoleDTO.setId(userOrganizationRole.getId());
                        //这里的时间是关联数据表的时间
                        userRole.setCreateTime(userOrganizationRole.getCreateTime());
                        roleList.add(userRole);
                        userDetailsRoleDTO.setRoleList(roleList);
                    }
                    detailsRoleDtoList.add(userDetailsRoleDTO);
                }
            }
        }
        user.setOrgRoleList(detailsRoleDtoList);
        return new CommonRlt(BasicRestStatusEnum.OK, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonRlt addUser(OperateUserReq request) {
        User user = new User();
        CommonRlt commonRlt = verifyInfo(user, request);
        if (StringUtil.isNotEmpty(commonRlt)) {
            return commonRlt;
        }
        if (StringUtil.isNotEmpty(request.getNo())) {
            Long count = userInfoMapper.isExistNo(0L, request.getNo(), request.getTenantId());
            if (count > 0) {
                return new CommonRlt(BasicRestStatusEnum.EXISTS_USER_ALREADY.code(), "当前工号已存在");
            }
        }

        //来源：APP注册0,后台注册1
        user.setSource(1);
        String salt = RandomStringUtils.getRandomString(10);
        user.setPassword(StringUtil.hashPassword(request.getPassWord().isEmpty() ? PASSWORD : request.getPassWord(), salt));
        user.setSalt(salt);
        user.setSex(request.getSex());
        user.setDeleteFlag(0);
        user.setNo(request.getNo());
        user.setRealName(request.getRealName());
        user.setCreateTime(DateUtils.getyyyyMMddHHmmss());
        user.setUpdateTime(DateUtils.getyyyyMMddHHmmss());
        user.setTenantId(request.getTenantId());
        //状态:0试用,1启动,2禁用
        user.setStatus(request.getStatus());
        user.setRoleId(request.getRoleId());
        this.save(user);
        //ID
        Long userId = (long) user.getId();
        UserOrganizationRole orgRole;
        //默认租户和角色
        Long defaultTenantId = request.getTenantId();
        Long defaultRoleId = 450187167612896100L;
        //用户对多组织多角色
        List<RoleOrgListDTO> roleOrgListDTOS = request.getRoleOrgList();
        Integer roleOrgIndex = 0;
       /* for (RoleOrgListDTO roleOrgListDTO : roleOrgListDTOS) {
            Integer roleIndex = 0;
            for (Long roleId : roleOrgListDTO.getRoleIds()) {
                orgRole = new UserOrganizationRole();
                orgRole.setId(uidGenerator.getUID());
                orgRole.setUserId(userId);
                orgRole.setRoleId(roleId);
                orgRole.setTenantId(roleOrgListDTO.getTenantId() == null ? request.getTenantId() : roleOrgListDTO.getTenantId());
                //orgRole.setIsDefault((roleOrgListDTOS.size()-1==roleOrgIndex && roleOrgListDTO.getRoleIds().length-1==roleIndex) ? 1 : 0);//先设置最后一条记录为默认组织角色
                orgRole.setDefaultFlag(0);
                if (roleOrgListDTOS.size() - 1 == roleOrgIndex && roleOrgListDTO.getRoleIds().length - 1 == roleIndex) {
                    orgRole.setDefaultFlag(1);
                    defaultTenantId = roleOrgListDTO.getTenantId();
                    defaultRoleId = roleId;
                }
                orgRole.setCreateTime(DateUtils.getyyyyMMddHHmmss());
                orgRole.setOrgId(roleOrgListDTO.getOrgId() == null ? 0 : roleOrgListDTO.getOrgId());
                userOrgRoleMapper.insert(orgRole);
                roleIndex++;
            }
            roleOrgIndex++;
        }*/

        orgRole = new UserOrganizationRole();
        orgRole.setId(uidGenerator.getUID());
        orgRole.setUserId(userId);
        orgRole.setRoleId(request.getRoleId());
        orgRole.setTenantId(request.getTenantId());
        userOrgRoleMapper.insert(orgRole);

        //更新用户表的租户和角色信息
//        user.setTenantId(defaultTenantId);
//        user.setRoleId(defaultRoleId);
//        this.updateById(user);
        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("用户管理");
        operateLog.setSmallModule("添加用户");
        operateLog.setTableName("user");
        operateLog.setType(OperateLogTypeEnum.INSERT);
        operateLog.setData1(userId + "");
        operateLogService.saveOperateLog(operateLog);
        return new CommonRlt(BasicRestStatusEnum.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonRlt editUser(OperateUserReq request) {
        User user = this.getById(request.getUserId());
        if (StringUtil.isEmpty(user)) {
            log.error(BasicRestStatusEnum.NOT_EXISTS_USER.subMessage());
            //Shift.fatalOnlyDetail(BasicRestStatusEnum.NOT_EXISTS_USER, "该用户不存在");
            throw new AccountException("该用户不存在");
        }
        if (StringUtil.isNotEmpty(request.getNo())) {
            Long count = userInfoMapper.isExistNo((long) request.getUserId(), request.getNo(), request.getTenantId());
            if (count > 0) {
                return new CommonRlt(BasicRestStatusEnum.EXISTS_USER_ALREADY.code(), "当前工号已存在");
            }
        }

        //校验
        CommonRlt commonRlt = verifyInfo(user, request);
        if (StringUtil.isNotEmpty(commonRlt)) {
            return commonRlt;
        }
        user.setLoginName(request.getLoginName());
        user.setRealName(request.getRealName());
        user.setNickName(request.getNickName());
        user.setMobile(request.getMobile());
        user.setNo(request.getNo());
        user.setUpdateTime(DateUtils.getyyyyMMddHHmmss());
        user.setTenantId(request.getTenantId());
        this.updateById(user);
        //ID
        Long userId = (long) user.getId();
        //用户组织角色关联信息
        List<UserOrganizationRole> userOrganizationRoleList = userInfoMapper.findUserOrgRoleList(userId);
        if (StringUtil.isEmpty(userOrganizationRoleList)) {
            //说明没有关系(数据非法)
            log.error(BasicRestStatusEnum.INVALID_SUBJECT_STATUS.subMessage());
            return new CommonRlt(BasicRestStatusEnum.INVALID_SUBJECT_STATUS, "当前用户组织角色为空");
        }
        List<Long> list = new ArrayList<>();
        for (UserOrganizationRole userOrgRole : userOrganizationRoleList) {
            list.add(userOrgRole.getId());
        }
        Long[] ids = list.toArray(new Long[list.size()]);
        Integer count = userInfoMapper.deleteUserOrgRole(ids);
        if (count < 0) {
            //如果这里失败,就进行回滚,不执行任何操作。
            log.error(BasicRestStatusEnum.ERROR_SERVER_UNKNOWN.subMessage());
            //return new CommonRlt(BasicRestStatusEnum.ERROR_SERVER_UNKNOWN, "服务端异常");
        }
        //默认租户和角色
        Long defaultTenantId = request.getTenantId();
        Long defaultRoleId = 450187167612896100L;

//        UserOrganizationRole orgRole;
////        List<RoleOrgListDTO> roleOrgList = request.getRoleOrgList();
////        Integer roleOrgIndex = 0;
////        for (RoleOrgListDTO dto : roleOrgList) {
////            Integer roleIndex = 0;
////            for (Long roleId : dto.getRoleIds()) {
////                //用户对多组织多角色
////                orgRole = new UserOrganizationRole();
////                orgRole.setId(uidGenerator.getUID());
////                orgRole.setRoleId(roleId);
////                orgRole.setUserId(userId);
////                orgRole.setTenantId(dto.getTenantId() == null ? request.getTenantId() : dto.getTenantId());
////                //orgRole.setIsDefault((roleOrgList.size()-1==roleOrgIndex && dto.getRoleIds().length-1==roleIndex) ? 1 : 0);//先设置最后一条记录为默认组织角色
////                orgRole.setDefaultFlag(0);
////                if (roleOrgList.size() - 1 == roleOrgIndex && dto.getRoleIds().length - 1 == roleIndex) {
////                    orgRole.setDefaultFlag(1);
////                    defaultTenantId = dto.getTenantId();
////                    defaultRoleId = roleId;
////                }
////                orgRole.setCreateTime(DateUtils.getyyyyMMddHHmmss());
////                orgRole.setOrgId(dto.getOrgId() == null ? 0 : dto.getOrgId());
////                userOrgRoleMapper.insert(orgRole);
////            }
////        }

        UserOrganizationRole orgRole = new UserOrganizationRole();
        orgRole.setId(uidGenerator.getUID());
        orgRole.setUserId(userId);
        orgRole.setRoleId(request.getRoleId());
        orgRole.setTenantId(request.getTenantId());
        userOrgRoleMapper.insert(orgRole);
        //更新用户表的租户和角色信息
        user.setTenantId(defaultTenantId);
        user.setRoleId(defaultRoleId);
        this.updateById(user);

        //保存操作日志
        OperateLog operateLog = new OperateLog();
        operateLog.setBigModule("用户管理");
        operateLog.setSmallModule("修改用户");
        operateLog.setTableName("user");
        operateLog.setType(OperateLogTypeEnum.UPDATE);
        operateLog.setData1(userId + "");
        operateLogService.saveOperateLog(operateLog);
        return new CommonRlt(BasicRestStatusEnum.OK);
    }

    @Override
    public CommonRlt resetPasswords(CommonIdsRequest request) {
        EditUserDto user = new EditUserDto();
        user.setIds(request.getIds());
        String salt = RandomStringUtils.getRandomString(10);
        user.setPassword(StringUtil.hashPassword(PASSWORD, salt));
        user.setSalt(salt);
        Integer count = userInfoMapper.updateByIds(user);
        //添加操作日志
        for (Long userId : request.getIds()) {
            OperateLog operateLog = new OperateLog();
            operateLog.setBigModule("用户管理");
            operateLog.setSmallModule("重置密码");
            operateLog.setTableName("user");
            operateLog.setType(OperateLogTypeEnum.UPDATE);
            operateLog.setData1(userId + "");
            operateLog.setData2(user.getPassword());
            operateLog.setData3(user.getSalt());
            operateLogService.saveOperateLog(operateLog);
        }
        if (count > 0) {
            return new CommonRlt(BasicRestStatusEnum.OK);
        }
        return new CommonRlt(BasicRestStatusEnum.FAIL);
    }


    public String returnUserName(User user, UserInfoReq LoginName, int num) {
        String name = RandomStringUtils.getRandomString(num);
        user.setLoginName(name);
        LoginName.setLoginName(name);
        user = userInfoMapper.findByUserName(LoginName);
        if (StringUtil.isNotEmpty(user)) {
            log.error("returnUserName is exists info:" + user);
            return returnUserName(user, LoginName, num);
        } else {
            return name;
        }
    }

    public String returnUserNickName(User user, UserInfoReq Mobile, int num) {
        String nickName = RandomStringUtils.getRandomString(num);
        user.setNickName(nickName);
        Mobile.setNickName(nickName);
        user = userInfoMapper.findByUserName(Mobile);
        if (StringUtil.isNotEmpty(user)) {
            log.error("returnUserNickName is exists info:" + user);
            return returnUserName(user, Mobile, num);
        } else {
            return nickName;
        }
    }

    public CommonRlt verifyInfo(User user, OperateUserReq request) {
        int numName = 8;
        //（第三方帐号获取的昵称会覆盖这个昵称）昵称为14位包含小写字母加上数字的随机组合。
        int numNickName = 14;
        user.setLoginName(request.getLoginName());
        user.setMobile(request.getMobile());
        //校验
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setLoginName(user.getLoginName());
        if (StringUtil.isNotEmpty(request.getUserId())) {
            user.setId(request.getUserId());
        }
        StringUtil stringUtil = new StringUtil();
        if (StringUtil.isNotEmpty(userInfoReq.getLoginName())) {
    /*        if (!userInfoReq.getLoginName().matches(stringUtil.regularLoginName)) {
                Shift.fatal(BasicRestStatusEnum.FAIL, userInfoReq.getLoginName() + ":账号格式不正确");
            }*/
            LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
            queryWrapper.eq(User::getLoginName, userInfoReq.getLoginName())
                    .eq(User::getDeleteFlag, 0);
            if (userInfoMapper.selectCount(queryWrapper) > 0) {
                if (StringUtil.isEmpty(request.getUserId())) {
                    log.error(BasicRestStatusEnum.EXISTS_USER_ALREADY.subMessage());
                    return new CommonRlt(BasicRestStatusEnum.EXISTS_USER_ALREADY, userInfoReq.getLoginName() + "用户已经注册");
                } else {
                    LambdaQueryWrapper<User> queryWrapper1 = new QueryWrapper<User>().lambda();
                    queryWrapper1.eq(User::getId, request.getUserId())
                            .eq(User::getDeleteFlag, 0);
                    User userName1 = userInfoMapper.selectOne(queryWrapper1);
                    if (!(request.getLoginName().equals(userName1.getLoginName()))) {
                        return new CommonRlt(BasicRestStatusEnum.EXISTS_USER_ALREADY, request.getLoginName() + "用户已经注册");
                    }
                }
            }
        }
        UserInfoReq Mobile = new UserInfoReq();
        Mobile.setMobile(user.getMobile());
        if (StringUtil.isNotEmpty(Mobile.getMobile())) {
            LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
            queryWrapper.eq(User::getMobile, Mobile.getMobile())
                    .eq(User::getDeleteFlag, 0);
            if (userInfoMapper.selectCount(queryWrapper) > 0) {
                if (StringUtil.isEmpty(request.getUserId())) {
                    log.error(BasicRestStatusEnum.EXISTS_USER_ALREADY.subMessage());
                    return new CommonRlt(BasicRestStatusEnum.EXISTS_USER_ALREADY, Mobile.getMobile() + "手机号已经注册");
                } else {
                    LambdaQueryWrapper<User> queryWrapper1 = new QueryWrapper<User>().lambda();
                    queryWrapper1.eq(User::getId, request.getUserId())
                            .eq(User::getDeleteFlag, 0);
                    User userName1 = userInfoMapper.selectOne(queryWrapper1);
                    if (!(request.getMobile().equals(userName1.getMobile()))) {
                        return new CommonRlt(BasicRestStatusEnum.EXISTS_USER_ALREADY, request.getMobile() + "手机号已经存在");
                    }
                }
            }
        }
        String nickName = request.getNickName();
        user.setNickName(nickName);
        //手机号填写，账号未填，自动分配一个账号，账号分配规则：数字加字母的8位组合（每个用户账号唯一）
        if (StringUtil.isNotEmpty(request.getMobile()) && StringUtil.isEmpty(user.getLoginName())) {
            String name = returnUserName(user, userInfoReq, numName);
            user.setLoginName(name);
        }
        if (StringUtil.isEmpty(request.getNickName())) {
            nickName = returnUserNickName(user, Mobile, numNickName);
            user.setNickName(nickName);
        }
        return null;
    }

    @Override
    public LoginRes UpdateUserOrg(UpdateUserOrgRequest request) {
        List<SysArg> sysArg = sysArgMapper.selectList(Wrappers.emptyWrapper());
        if (sysArg.size() == 0) {
            //Shift.fatalOnlyDetail(HttpStatusCodesEnum.ERROR_SMS_FAIL, "系统参数错误");
            throw new AccountException("系统参数错误");
        }
        Long roleId = userOrgRoleService.findFirstRoleByUserIdAndTenantId(request.getUserId(), request.getTenantId());
        if (roleId == null) {
            //Shift.fatalOnlyDetail(BasicRestStatusEnum.INVALID_SUBJECT_CREDENTIALS, "用户组织信息不存在");
            throw new AccountException("用户组织信息不存在");
        }
        //更新默认租户和角色
        User user = new User();
        user.setId(request.getUserId().intValue());
        user.setTenantId(request.getTenantId());
        user.setRoleId(roleId);
        this.updateById(user);
        //同步更新关系表中的默认显示标记，先关联表中的默认组织角色字段维护起来
        userOrgRoleService.setDefaultByUserId(request.getUserId());
        userOrgRoleService.setDefaultFlag(request.getUserId(), user.getTenantId(), user.getRoleId());
        List<LoginRes> list = userInfoMapper.findUserById(request.getUserId());
        if (list == null || list.size() == 0) {
            Shift.fatal(BasicRestStatusEnum.NOT_EXISTS_USER);
        } else if (list.size() > 1) {
            //Shift.fatalOnlyDetail(BasicRestStatusEnum.USER_ACCOUNT_ERR, "用户名出现重复");
            throw new AccountException("用户名出现重复");
        }
        LoginRes loginRes = list.get(0);
        loginRes.setSmsServer(sysArg.get(0).getSmsServer());
        //外网环境
        if (request.getNetType() != null && request.getNetType() == 1) {
            //dfsServer for Ios
            loginRes.setDfsServer(sysArg.get(0).getDfsServerLocal());
            loginRes.setDfsUploadServer(sysArg.get(0).getUploadServerLocal());
        } else {
            loginRes.setDfsServer(sysArg.get(0).getDfsServer());
            loginRes.setDfsUploadServer(sysArg.get(0).getUploadServer());
        }
        List<UserOrgRoleDTO> userOrgRoleDTOList = userOrgRoleService.listUserOrgRoleByUserId(Long.valueOf(loginRes.getUserId()), loginRes.getTenantId());
//        res.setRoleNames(StringUtils.join(arrayList ,","));
        loginRes.setUserOrgRoleList(userOrgRoleDTOList);
        return loginRes;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonRlt addBatchImport(Long orgId, String fileName, MultipartFile file) {
        int size = 500;
        ReadExcelUtils excelUtil = new ReadExcelUtils();
        //确定当前组织文件
        Workbook workbook = excelUtil.ReadExcelUtils(fileName, file);
        int index = fileName.indexOf(".");
        String orgName = fileName.substring(0, index);
        if (StringUtil.isEmpty(workbook)) {
            Shift.fatal(BasicRestStatusEnum.FAIL, "文件不是Excel文件");
        }
        //组织ID,查询组织信息(校验:组织模板交换使用)
        Organization org = organizationMapper.selectById(orgId);
        if (StringUtil.isEmpty(org)) {
            //可能出现参数问题
            Shift.fatal(BasicRestStatusEnum.FAIL, "组织信息有误");
        }
        if (!(orgName.equals(org.getName()))) {
            Shift.fatal(BasicRestStatusEnum.FAIL, "上传模板名称,请修改成登陆学校名称");
        }
        Long tenantId = org.getTenantId();
        // excel文件
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum > size) {
            log.error(BasicRestStatusEnum.FAIL.subMessage());
            Shift.fatal(BasicRestStatusEnum.FAIL, "导入数据已超出500条");
        }
        Set<String> setName = new HashSet<>();
        Set<String> setMobile = new HashSet<>();
        //文件账号
        try {
            for (int i = 2; i <= lastRowNum; i++) {
                Cell s = sheet.getRow(i).getCell(0);
                if (!setName.add(s.toString())) {
                    Shift.fatal(BasicRestStatusEnum.FAIL, "第" + (i) + "行,账号:" + s + "在表格中有重复");
                }
            }
            //文件手机号
            for (int i = 2; i <= lastRowNum; i++) {
                //sheet.getRow(i).getCell(1).setCellType(CellType.STRING);
                Cell s = sheet.getRow(i).getCell(1);
                if (!setMobile.add(s.toString())) {
                    Shift.fatal(BasicRestStatusEnum.FAIL, "第" + (i) + "行,手机号:" + s + "在表格中有重复");
                }
            }
            for (int i = 2; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                //校验
                User user = new User();
                checkRow(user, row, orgName);
            }
            for (int i = 2; i <= lastRowNum; i++) {
                //获取行
                getSingleRow(orgId, sheet, i, tenantId);
            }
        } catch (Exception e) {
            Shift.fatal(BasicRestStatusEnum.FAIL, ":文档编辑出现失误,请删除行修改文档");
        }
        return new CommonRlt(BasicRestStatusEnum.OK);
    }

    @Override
    public LoginRes findUserToken(String token) {
        return userAuxiliary.getLoginRes();
    }

    @Override
    public LoginRes findUser(String userName) throws Exception {
        List<GetSysMenuResponse> getSysMenuResponseVo = new ArrayList<>();
        LoginRes loginRes = userInfoMapper.findByLoginName(userName).stream().findFirst().orElse(null);
        try {
            loginRes.getSysMenuResponses().stream().filter(t -> t.getLevel().equals(1)).collect(Collectors.toList()).forEach(c ->
            {
                if (c.getId().equals(c.getParentId()))
                    return;
                c.setChildren(this.getSysMenuResponseChild(loginRes.getSysMenuResponses(), c));
                getSysMenuResponseVo.add(c);
            });
        } catch (Exception ex) {
            throw new Exception();
        }
        loginRes.setSysMenuResponses(getSysMenuResponseVo);
        return loginRes;
    }

    public List<GetSysMenuResponse> getSysMenuResponseChild(List<GetSysMenuResponse> sysMenuResponses, GetSysMenuResponse sysMenuResponse) {
        //t 中数据数据进行遍历
        List<GetSysMenuResponse> children = sysMenuResponses.stream().filter(e -> e.getParentId() != null && e.getParentId().equals(sysMenuResponse.getId())).collect(Collectors.toList());
        if (children.size() > 1) {
            sysMenuResponse.setChildren(children);
            children.forEach(t -> {
                this.getSysMenuResponseChild(sysMenuResponses, t);
            });
        }
        return children;
    }


    public CommonRlt getSingleRow(Long orgId, Sheet sheet, int rowNum, Long tenantId) {
        Row row = sheet.getRow(rowNum);
        User user = new User();
        //每一条row的大小
        List<String> ls = new ArrayList<>();
        int lastCellNum = row.getLastCellNum();
        for (int j = 0; j < lastCellNum; j++) {
            ls.add(row.getCell(j).toString());
        }
        String loginName = ls.get(0);
        String mobile = ls.get(1);
        String roleName = ls.get(2);
        String orgName = ls.get(3);
        String realName = ls.get(4);
        String sex = ls.get(5);
        List list = null;
        //生成组织名称
        if (StringUtil.isEmpty(orgName)) {
            LambdaQueryWrapper<Organization> queryWrapper = new QueryWrapper<Organization>().lambda();
            queryWrapper.eq(Organization::getTenantId, tenantId)
                    .eq(Organization::getParentId, 0)
                    .eq(Organization::getDeleteFlag, 0);
            Organization org = organizationMapper.selectOne(queryWrapper);
            list = StringUtil.changeStringList(org.getName());
        } else {
            list = StringUtil.changeStringList(orgName);
            HashSet<Integer> hashSet = new HashSet<>(list);
            if (list.size() != hashSet.size()) {
                Shift.fatal(BasicRestStatusEnum.FAIL, list + ":信息不能重名,请修改文档");
            }
        }
        //获取组织集合
        List<Organization> orgList;
        List<RoleOrgListDTO> roleOrgListDTO = new ArrayList<>();
        RoleOrgListDTO roleOrgListObject = new RoleOrgListDTO();
        // {id:ids[]}
        LambdaQueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().lambda();
        queryWrapper.eq(Role::getRoleName, roleName)
                .eq(Role::getTenantId, tenantId)
                .eq(Role::getDeleteFlag, 0);
        Role roleObject = roleMapper.selectOne(queryWrapper);
        if (StringUtil.isEmpty(roleObject)) {
            //请对比数据库,该组织的角色是否存在
            Shift.fatal(BasicRestStatusEnum.FAIL, roleName + ":该角色信息不存在,请修改文档");
        }
        //集合转换下拉单条
        List<Long> roleList = new ArrayList<>();
        roleList.add(roleObject.getId());
        Long[] roleIds = roleList.toArray(new Long[0]);
        //组织
        for (int i = 0; i < list.size(); i++) {
            LambdaQueryWrapper<Organization> queryWrapper1 = new QueryWrapper<Organization>().lambda();
            queryWrapper1.eq(Organization::getName, list.get(i).toString())
                    .eq(Organization::getTenantId, tenantId)
                    .eq(Organization::getDeleteFlag, 0);
            orgList = organizationMapper.selectList(queryWrapper1);
            if (StringUtil.isEmpty(orgList)) {
                //请对比数据库,该组织的分类是否存在
                Shift.fatal(BasicRestStatusEnum.FAIL, list.get(i).toString() + ":该分类信息不存在,请修改文档");
            }
            for (Organization org : orgList) {
                Long id = org.getId();
                //格式
                roleOrgListObject.setOrgId(id);
                roleOrgListObject.setRoleIds(roleIds);
                roleOrgListDTO.add(roleOrgListObject);
            }
        }
        //来源：APP注册0,后台注册1
        user.setSource(1);
        //加密加盐。
        String salt = RandomStringUtils.getRandomString(10);
        user.setPassword(StringUtil.hashPassword(PASSWORD, salt));
        user.setLoginName(loginName);
        user.setMobile(mobile);
        user.setSalt(salt);
        //默认角色ID
        user.setRoleId(roleObject.getId());
        String sexMan = "男";
        String sexShe = "女";
        if (sexMan.equals(sex)) {
            user.setSex(0);
        } else if (sexShe.equals(sex)) {
            user.setSex(1);
        }
        user.setDeleteFlag(0);
        user.setRealName(realName);
        user.setCreateTime(DateUtils.getyyyyMMddHHmmss());
        user.setUpdateTime(DateUtils.getyyyyMMddHHmmss());
        user.setTenantId(189516514267758612L);
        //状态:0试用,1启动,2禁用
        user.setStatus(1);
        userInfoMapper.insert(user);
        Long userId = (long) user.getId();
        UserOrganizationRole orgRole;
        //用户对多组织单角色
        List<RoleOrgListDTO> roleOrgListDTOS = roleOrgListDTO;
        for (RoleOrgListDTO roleOrgList : roleOrgListDTOS) {
            for (Long roleId : roleOrgList.getRoleIds()) {
                //绑定
                orgRole = new UserOrganizationRole();
                orgRole.setId(uidGenerator.getUID());
                orgRole.setUserId(userId);
                orgRole.setCreateTime(DateUtils.getyyyyMMddHHmmss());
                orgRole.setTenantId(orgId);
                orgRole.setDefaultFlag(0);
                orgRole.setRoleId(roleId);
                orgRole.setOrgId(roleOrgList.getOrgId() == null ? 0 : roleOrgList.getOrgId());
                userOrgRoleMapper.insert(orgRole);
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonRlt register(AddUserRegister request) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YYYYMMDDHHMMSS2);
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper.eq(User::getMobile, request.getMobile())
                .eq(User::getDeleteFlag, 0);
        if (userInfoMapper.selectCount(queryWrapper) > 0) {
            //手机已注册
            log.error(BasicRestStatusEnum.EXISTS_USER_ALREADY.subMessage());
            return new CommonRlt(BasicRestStatusEnum.EXISTS_USER_ALREADY);
        }
        String code = request.getSmsCode();
        //注册方式-验证码错误
        LambdaQueryWrapper<SmsRecord> queryWrapper2 = new QueryWrapper<SmsRecord>().lambda();
        queryWrapper2.eq(SmsRecord::getMobile, request.getMobile())
                .eq(SmsRecord::getCaptcha, code)
                .eq(SmsRecord::getOperationFlag, 1);
        List<SmsRecord> smsRecordList = smsRecordMapper.selectList(queryWrapper2);
        if (smsRecordList == null || smsRecordList.size() == 0) {
            log.error(BasicRestStatusEnum.EXCEED_CAPTCHA_ERROE.subMessage());
            return new CommonRlt(BasicRestStatusEnum.EXCEED_CAPTCHA_ERROE);
        }
        //验证码失效
        LambdaQueryWrapper<SmsRecord> queryWrapper3 = new QueryWrapper<SmsRecord>().lambda();
        queryWrapper3.eq(SmsRecord::getMobile, request.getMobile())
                .eq(SmsRecord::getCaptcha, code)
                .eq(SmsRecord::getDeleteFlag, 0)
                .eq(SmsRecord::getOperationFlag, 1);
        SmsRecord smsRecord = smsRecordMapper.selectOne(queryWrapper3);
        if (StringUtil.isEmpty(smsRecord)) {
            log.error(BasicRestStatusEnum.EXPIRED_VERIFICATION_CODE.subMessage());
            return new CommonRlt(BasicRestStatusEnum.EXPIRED_VERIFICATION_CODE);
        }

        String endNum = smsRecordList.get(0).getEndTime();
        String beginNum = sdf.format(System.currentTimeMillis());
        Date beginTime = sdf.parse(beginNum);
        Date endTime = sdf.parse(endNum);
        //1000 * 60 * 60 * 24
        if ((beginTime.getTime() - endTime.getTime()) >= SECONDS) {
            log.error(BasicRestStatusEnum.EXCEED_CAPTCHA_OVERDUE.subMessage());
            return new CommonRlt(BasicRestStatusEnum.EXCEED_CAPTCHA_OVERDUE);
        }
        User user = new User();
        OperateUserReq requests = new OperateUserReq();
        //昵称生成,账号名称生成
        requests.setMobile(request.getMobile());
        CommonRlt commonRlt = verifyInfo(user, requests);
        if (StringUtil.isNotEmpty(commonRlt)) {
            return commonRlt;
        }
        //来源：APP注册0,后台注册1
        user.setSource(0);
        String newPwd = RSAEncryptUtils.decrypt(request.getPassword(), privateKey);
        String salt = RandomStringUtils.getRandomString(10);
        user.setPassword(StringUtil.hashPassword(newPwd, salt));
        user.setSalt(salt);
        user.setDeleteFlag(0);
        user.setSmsCode(code);
        user.setCreateTime(DateUtils.getyyyyMMddHHmmss());
        user.setUpdateTime(DateUtils.getyyyyMMddHHmmss());

        user.setTenantId(189516514267758612L);
        //状态:0试用,1启动,2禁用
        user.setStatus(1);
        userInfoMapper.insert(user);
        //销毁
        smsRecord.setDeleteFlag(1);
        smsRecordMapper.update(smsRecord, null);
        //关联默认学生
        UserOrganizationRole role = new UserOrganizationRole();
        role.setId(uidGenerator.getUID());
        role.setUserId((long) user.getId());
        //无组织为0
        role.setOrgId((long) 0);
        //默认,学生角色ID
        final Long roleId = 450187167612896100L;
        role.setRoleId(roleId);
        role.setCreateTime(DateUtils.getyyyyMMddHHmmss());
        userOrgRoleMapper.insert(role);
        return new CommonRlt(BasicRestStatusEnum.OK);
    }

    public CommonRlt checkRow(User user, Row row, String orgName) {
        //row.getFirstCellNum() 0 1 2 4
        String loginName = row.getCell(0).getStringCellValue();
        StringUtil stringUtil = new StringUtil();
        if (StringUtil.isNotEmpty(loginName)) {
            if (!loginName.matches(stringUtil.regularLoginName)) {
                Shift.fatal(BasicRestStatusEnum.FAIL, loginName + ":账号格式不正确");
            }
        }
        //row.getCell(1).setCellType(CellType.STRING);
        String mobile = row.getCell(1).getStringCellValue();
        if (StringUtil.isNotEmpty(mobile)) {
            if (!mobile.matches(stringUtil.regularMobile)) {
                Shift.fatal(BasicRestStatusEnum.FAIL, mobile + ":手机格式不正确");
            }
        }
        OperateUserReq request = new OperateUserReq();
        request.setLoginName(loginName);
        request.setMobile(mobile);
        CommonRlt commonRlt = verifyInfo(user, request);
        if (StringUtil.isNotEmpty(commonRlt)) {
            return commonRlt;
        }
        String stringLoginName = user.getLoginName();
        if (StringUtil.isNotEmpty(stringLoginName)) {
            row.createCell(0).setCellValue(stringLoginName);
        }
        String stringMobile = user.getMobile();
        if (StringUtil.isNotEmpty(stringMobile)) {
            row.createCell(1).setCellValue(stringMobile);
        }
        String role = row.getCell(2).getStringCellValue();
        if (StringUtil.isEmpty(role)) {
            Shift.fatal(BasicRestStatusEnum.FAIL, "请补全Excel文件中角色内容");
        }
        if (!role.matches(stringUtil.regularRole)) {
            Shift.fatal(BasicRestStatusEnum.FAIL, role + ":角色格式不正确");
        }
        String cellOrgName = row.getCell(3).getStringCellValue();
        if (StringUtil.isEmpty(cellOrgName)) {
            //文件名称
            row.createCell(3).setCellValue(orgName);
        }
        return null;
    }

}
