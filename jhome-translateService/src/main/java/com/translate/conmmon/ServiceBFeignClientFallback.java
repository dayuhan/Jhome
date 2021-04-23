package com.translate.conmmon;

import com.domain.common.PermissionContext;

public class ServiceBFeignClientFallback implements UserRemoteServiceInterface {

    @Override
    public String getSession(String sessionId) {
        return null;
    }

    @Override
    public String createSession(String sessionJson) {
        return null;
    }

    @Override
    public String updateSession(String sessionJson) {
        return null;
    }


    @Override
    public String deleteSession(String sessionJson) {
        return null;
    }

    @Override
    public PermissionContext getPermissions(String username) {
        return null;
    }

    @Override
    public String getCallbackUrl() {
        return null;
    }


}
