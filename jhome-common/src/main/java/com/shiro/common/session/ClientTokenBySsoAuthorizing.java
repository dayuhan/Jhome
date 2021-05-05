package com.shiro.common.session;

import com.alibaba.fastjson.JSON;
import com.shiro.common.SessionDaoZH;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * SessionDao
 *
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-06-03 22:50
 **/
public abstract class ClientTokenBySsoAuthorizing extends CachingSessionDAO {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private RemoteBaseInterface remoteService;
    private String appKey;

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    protected Serializable doCreate(Session session) {
        ShiroSession shiroSession = null;
        Serializable sessionId = null;
        /*执行模板方法，如果是单点登陆过来的直接获取缓存中的session，无需在创建*/
        shiroSession = (ShiroSession) this.doGetSSoBySession();
        //判断是否有单点登陆认证过来的Session
        if (shiroSession != null) {
            sessionId = shiroSession.getId();
            //session = shiroSession;
            //复制session,把缓存中的session重新拷贝一份

        } else {
            //远程生成
            try {
                sessionId= remoteService.createSession(JSON.toJSONString(session));
            } catch (Exception ex) {
                logger.info(String.format("createSession error :%s", ex.getMessage().toString()));
            }
        }
        assignSessionId(session, sessionId);
        session.setAttribute("AUTHENTICATED_SESSION_KEY", boolean.class);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        String sessionId = (String) serializable;
        try {
            String shiroSessionJson = remoteService.getSession(sessionId);
            ShiroSession shiroSession = JSON.parseObject(shiroSessionJson, ShiroSession.class);
            return SessionDaoZH.SerializedStringToAttributeBean(shiroSession);
        } catch (Exception ex) {
            logger.info(String.format("doReadSession error :%s", ex.getMessage().toString()));
            return null;
        }
    }

    @Override
    protected void doUpdate(Session session) {
        try {
            if (session instanceof SimpleSession) {
                ShiroSession shiroSession = (ShiroSession) session;
                if (!shiroSession.isChanged()) {
                    return;
                }
                remoteService.updateSession(JSON.toJSONString(shiroSession));
            }
        } catch (Exception ex) {
            logger.info(String.format("doUpdate error :%s", ex.getMessage().toString()));
        }

    }

    @Override
    protected void doDelete(Session session) {
        try {
            remoteService.deleteSession(JSON.toJSONString(session));
        } catch (Exception ex) {
            logger.info(String.format("doDelete error :%s", ex.getMessage().toString()));
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }

    public RemoteBaseInterface getRemoteService() {
        return remoteService;
    }

    public void setRemoteService(RemoteBaseInterface remoteService) {
        this.remoteService = remoteService;
    }

    /**
     * 分配Session
     *
     * @return
     */
    protected abstract Session doGetSSoBySession();


}
