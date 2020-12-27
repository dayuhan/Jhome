package com.shiro.common.session;

import com.bracket.common.Identity.UserUtil;
import com.bracket.common.ToolKit.StringUtil;
import org.apache.shiro.session.Session;

/**
 * 关键一步实现调用服务器的RedisSessionDao 实现对统一数据源操作
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class ClientSessionDAO extends ClientTokenBySsoAuthorizing {
    private String ssoToken;

    @Override
    protected Session doGetSSoBySession() {
        //解析ssoToken
        String sessionId = "";
        if (StringUtil.isNotBlank(ssoToken)) {
            //解析Tonken
            //sessionId = (String) UserUtil.ParsingToken(this.getSsoToken());
            //根据ssToken获取Session
            Session session = super.doReadSession(this.getSsoToken());
            return session;
        }
        return null;
    }

    public String getSsoToken() {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    @Override
    public void setRemoteService(RemoteBaseInterface remoteService) {
        super.setRemoteService(remoteService);
    }


}
