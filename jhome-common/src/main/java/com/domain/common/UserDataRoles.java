package com.domain.common;

import java.io.Serializable;

/**
 * 用户数据角色
 */
public class UserDataRoles implements Serializable {
    public UserDataRoles() {
    }
    private static final long serialVersionUID = -8995288721315613412L;

    private Long id;
    private Long roleId;
    private Long sysOrganizationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getSysOrganizationId() {
        return sysOrganizationId;
    }

    public void setSysOrganizationId(Long sysOrganizationId) {
        this.sysOrganizationId = sysOrganizationId;
    }
}
