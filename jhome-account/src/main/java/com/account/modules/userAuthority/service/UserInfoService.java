package com.account.modules.userAuthority.service;


import com.account.common.CommonRlt;
import com.account.modules.userAuthority.domain.User;
import com.account.modules.userAuthority.model.request.*;
import com.account.modules.userAuthority.model.response.ListUserInfoRes;
import com.account.modules.userAuthority.model.response.LoginRes;
import com.account.modules.userAuthority.model.response.UcTokenRes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserInfoService extends IService<User> {

    UcTokenRes loginByAccount(LoginAccountReq request, HttpServletRequest httpRequest);

    UcTokenRes loginByCode(LoginCodeReq request, HttpServletRequest httpRequest);

    CommonRlt loginOut(HttpServletRequest request);

    CommonRlt resetPasswords(CommonIdsRequest request);

    CommonRlt switchStatus(EditUserStatusReq request);

    CommonRlt batchDelete(CommonIdsRequest request);

    IPage<ListUserInfoRes> findUserList(ListUserReq request);

    CommonRlt getUserDetails(Long id);

    CommonRlt addUser(OperateUserReq request);

    CommonRlt editUser(OperateUserReq request);

    LoginRes UpdateUserOrg(UpdateUserOrgRequest request);

    CommonRlt register(AddUserRegister request) throws Exception;

    CommonRlt addBatchImport(Long orgId, String fileName, MultipartFile file);

    LoginRes findUserToken(String token);

    LoginRes findUser(String userName) throws Exception;
}