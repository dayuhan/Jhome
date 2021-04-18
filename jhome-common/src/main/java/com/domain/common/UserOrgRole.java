package com.domain.common;

import java.io.Serializable;

/**
 * 功能权限
 */
public class UserOrgRole implements Serializable {
    private static final long serialVersionUID = -9212716892340286541L;
    private String tenantName;
    private String roleNames;
    private Long tenantId;
    private Integer defaultFlag;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Integer defaultFlag) {
        this.defaultFlag = defaultFlag;
    }
}
