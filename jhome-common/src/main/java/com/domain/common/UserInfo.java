package com.domain.common;/**
 * @program: account-root
 * @description
 * @author: Daxv
 * @create: 2020-07-09 23:36
 **/

import com.alibaba.fastjson.JSONObject;
import com.bracket.common.ToolKit.JSONUtils;
import jdk.nashorn.internal.parser.JSONParser;

import java.util.List;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: account-root
 * @description: 用户信息
 * @author: Daxv
 * @create: 2020-07-09 23:36
 **/
public class UserInfo extends DefaultUserInfo {
    private String gender; //性别
    private String productId; //产品ID
    private String realName; //昵称
    private String phone; //电话
    private String tenantId; //租户ID
    private String schoolName; //学校名称
    private String roleNames; //角色名称
    private String remotelyToken; //远程返回token
    private List<SysMenu> SysMenu;//用户功能权限
    private List<UserDataRoles> userDataRoles;//用户数据权限

    /**
     * 盐值
     */
    private String salt;

    public List<UserDataRoles> getUserDataRoles() {
        return userDataRoles;
    }

    public void setUserDataRoles(List<UserDataRoles> userDataRoles) {
        this.userDataRoles = userDataRoles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<com.domain.common.SysMenu> getSysMenu() {
        return SysMenu;
    }

    public void setSysMenu(List<com.domain.common.SysMenu> sysMenu) {
        SysMenu = sysMenu;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRemotelyToken() {
        return remotelyToken;
    }

    public void setRemotelyToken(String remotelyToken) {
        this.remotelyToken = remotelyToken;
    }

    @Override
    public String toString() {
        return JSONUtils.beanToJson(this);
    }

}
