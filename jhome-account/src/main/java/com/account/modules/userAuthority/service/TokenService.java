package com.account.modules.userAuthority.service;


import com.account.modules.userAuthority.model.response.LoginRes;

public interface TokenService {

    /**
     * 会话超时时间
     * SESSION_UNIT:单位
     */
    public final static int SESSION_UNIT_HOUR = 4;

    /**
     * 会话超时时间
     * SESSION_UNIT:单位
     */
    public final static int SESSION_UNIT_DAY = 30;

    /**
     * 会话超时时间
     * 默认2h
     */
    public final static int SESSION_TIMEOUT = 4 * 60 * 60;
    /**
     * 置换保护时间
     * 默认1h
     */
    public final static int REPLACEMENT_PROTECTION_TIMEOUT = 60 * 60;
    /**
     * 旧token延迟过期时间
     * 默认2min
     */
    public final static int REPLACEMENT_DELAY = 2 * 60;

    /**
     * 生成token
     */
    public String generateToken(String agent, LoginRes user);

    /**
     * 保存用户信息至redis
     * ,
     *
     * @param token
     * @param user
     */
    public void save(String token, LoginRes user);

    /**
     * 从redis获取用户信息
     *
     * @param token
     * @return
     */
    public LoginRes getLoadUser(String token);

    /**
     * 移除
     *
     * @param token
     */
    public void delete(String token);

    /**
     * 验证token是否有效
     *
     * @param agent
     * @param token
     * @return
     */
    public boolean validate(String agent, String token);

}
