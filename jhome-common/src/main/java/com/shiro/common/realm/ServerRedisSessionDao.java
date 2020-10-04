package com.shiro.common.realm;

import com.bracket.common.ToolKit.StringUtil;
import com.domain.common.UserInfo;
import com.shiro.common.SessionDaoZH;
import com.shiro.common.token.DeviceType;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 远程会话
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class ServerRedisSessionDao extends AbstractSessionDAO {
    protected final long PC_EXPIRE_TIME = 600;
    protected final long APP_EXPIRE_TIME = 600;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    //redis操作类
    private RedisTemplate redisTemplate;
    //过期时间
    private long expiredTime;

    public ServerRedisSessionDao() {
    }

    //用户第一次访问系统时创建会话信息
    @Override
    protected Serializable doCreate(Session session) {
        logger.debug("用户第一次访问系统时创建会话信息");
        SessionDaoZH.SerializedBeanToString(session);
        Serializable sessionId = SessionCons.TOKEN_PREFIX
                + UUID.randomUUID().toString();
        assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(sessionId, session);
        redisTemplate.expire(sessionId, expiredTime, TimeUnit.SECONDS);
        if (logger.isDebugEnabled()) {
            logger.debug("create shiro session ,sessionId is :{}",
                    sessionId.toString());
        }
        return sessionId;
    }
    //读取会话信息
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("读取会话信息");
        Session session = (Session) redisTemplate.opsForValue().get(sessionId);
        //SerializedStringToBean(session);
        if (null != session) {
            String deviceType = (String) session.getAttribute(SessionCons.DEVICE_TYPE);
            if (StringUtil.isNotBlank(deviceType)) {
                if (deviceType.equals(DeviceType.PC.toString())) {
                    // PC会话信息
                    session.setTimeout(expiredTime * 1000);
                    if (logger.isDebugEnabled()) {
                        logger.debug("read pc session ,sessionId is :{}",
                                sessionId.toString());
                    }
                } else {
                    // APP会话信息
                    session.setTimeout(expiredTime * 1000);
                    if (logger.isDebugEnabled()) {
                        logger.debug("read app session ,sessionId is :{}",
                                sessionId.toString());
                    }
                }
            }
        }
     /*   else {
            //远程调用接口执行时 创建：解决 客户端没有清除缓存的问题
            ShiroSession shiroSession = new ShiroSession();
            shiroSession.setId(sessionId);
            assignSessionId(shiroSession, sessionId);
            redisTemplate.opsForValue().set(sessionId, shiroSession);
            redisTemplate.expire(sessionId, PC_EXPIRE_TIME, TimeUnit.SECONDS);
            return  shiroSession;
        }*/
        return session;
    }

    //更新用户会话信息
    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.debug("更新用户会话信息");
        SessionDaoZH.SerializedBeanToString(session);
        //获取缓存中的session
        Session sessionOld = (Session) redisTemplate.opsForValue().get(session.getId());
        //合并新老session
        if (sessionOld != session)
            session = SessionDaoZH.MergeAttributeBean(sessionOld, session);
        //增量更新到Redis中
        if (null != session && null != session.getId()) {
            String deviceType = (String) session
                    .getAttribute(SessionCons.DEVICE_TYPE);

            if (StringUtil.isBlank(deviceType))
                deviceType = DeviceType.PC.toString();
            //session.setAttribute(SessionCons.DEVICE_TYPE,deviceType);
            redisTemplate.opsForValue().set(session.getId(), session);
            if (deviceType.equals(DeviceType.PC.toString())) {
                // PC会话信息
                session.setTimeout(expiredTime * 1000);
                redisTemplate.expire(session.getId(), expiredTime,
                        TimeUnit.SECONDS);
                if (logger.isDebugEnabled()) {
                    logger.debug("update pc session ,sessionId is :{}", session
                            .getId().toString());
                }
            } else {
                // APP会话信息
                session.setTimeout(expiredTime * 1000);
                redisTemplate.expire(session.getId(), expiredTime,
                        TimeUnit.SECONDS);
                if (logger.isDebugEnabled()) {
                    logger.debug("update app session ,sessionId is :{}",
                            session.getId().toString());
                }
            }
        } else {
            logger.debug("Session是空没有读到");
        }
    }

    //删除用户会话信息
    @Override
    public void delete(Session session) {
        logger.debug("删除用户会话信息");
        if (logger.isDebugEnabled()) {
            logger.debug("delete shiro session ,sessionId is :{}", session
                    .getId().toString());
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    //获取所有的在线会话信息
    @Override
    public Collection<Session> getActiveSessions() {
        logger.debug("获取所有的在线会话信息");
        Set<Serializable> keys = redisTemplate
                .keys(SessionCons.TOKEN_PREFIX_KEY);
        if (keys.size() == 0) {
            return Collections.emptySet();
        }
        List<Session> sessions = redisTemplate.opsForValue().multiGet(keys);
        return Collections.unmodifiableCollection(sessions);
    }

    /**
     * cas 创建session
     * @param session
     * @param userInfo
     * @return
     */
    public Serializable doCreateByUserInfo(Session session, UserInfo userInfo) {
        logger.debug("cas 创建session");
        SessionDaoZH.SerializedBeanToString(session);
        assignSessionId(session, userInfo.getJhomeToken());
        redisTemplate.opsForValue().set(userInfo.getJhomeToken(), session);
        redisTemplate.expire(userInfo.getJhomeToken(), expiredTime, TimeUnit.SECONDS);
        if (logger.isDebugEnabled()) {
            logger.debug("create shiro session ,sessionId is :{}",
                    userInfo.getJhomeToken().toString());
        }
        return userInfo.getJhomeToken();
    }


    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
