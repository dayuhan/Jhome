package com.account.common.shiro.filter.cas;

import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Identity.UserUtil;
import com.bracket.common.ToolKit.JSONUtils;
import com.bracket.common.ToolKit.StringUtil;
import com.shiro.common.realm.ServerRedisSessionDao;
import io.buji.pac4j.context.ShiroSessionStore;
import io.buji.pac4j.engine.ShiroSecurityLogic;
import io.buji.pac4j.filter.SecurityFilter;
import org.apache.shiro.session.Session;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.engine.SecurityLogic;
import org.pac4j.core.http.adapter.J2ENopHttpActionAdapter;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

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
 * @program: jhome-root
 * @description: SecurityFilter
 * @author: Daxv
 * @create: 2020-07-23 17:00
 **/
public class CasSecurityFilter extends SecurityFilter {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private SecurityLogic<Object, J2EContext> securityLogic = new ShiroSecurityLogic();
    public ServerRedisSessionDao cDao;
    private Config config;
    private String clients;
    private String authorizers;
    private String matchers;
    private Boolean multiProfile;

    public CasSecurityFilter() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ResponseJson result = new ResponseJson();
        String token = UserUtil.GetToken(servletRequest, servletResponse);
        CommonHelper.assertNotNull("securityLogic", this.securityLogic);
        CommonHelper.assertNotNull("config", this.config);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        SessionStore<J2EContext> sessionStore = this.config.getSessionStore();
        J2EContext context = new J2EContext(request, response, (SessionStore) (sessionStore != null ? sessionStore : ShiroSessionStore.INSTANCE));
        //校验失效
        if (StringUtil.isNotBlank(token)) {

            try {
                Session session = cDao.readSession(token);
                if (session == null) {
                    logger.debug("单点登陆本地session已经失效");
                }
                String userBs = (String) session.getAttribute("pac4jUserProfiles");
                CommonProfile commonProfile = new CommonProfile();
                commonProfile = (CommonProfile) JSONUtils.jsonToBean(userBs, commonProfile);
                LinkedHashMap profiles = new LinkedHashMap();
                profiles.put("SmartCampus", commonProfile);
                context.setRequestAttribute("pac4jUserProfiles", commonProfile);
            } catch (Exception ex) {
                logger.debug("单点登陆本地session已经失效");
            }
        }
        this.securityLogic.perform(context, this.config, (ctx, profiles, parameters) -> {
            filterChain.doFilter(request, response);
            return null;
        }, J2ENopHttpActionAdapter.INSTANCE, this.clients, this.authorizers, this.matchers, this.multiProfile, new Object[0]);
    }



    @Override
    public SecurityLogic<Object, J2EContext> getSecurityLogic() {
        return securityLogic;
    }

    @Override
    public void setSecurityLogic(SecurityLogic<Object, J2EContext> securityLogic) {
        this.securityLogic = securityLogic;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public String getClients() {
        return clients;
    }

    @Override
    public void setClients(String clients) {
        this.clients = clients;
    }

    @Override
    public String getAuthorizers() {
        return authorizers;
    }

    @Override
    public void setAuthorizers(String authorizers) {
        this.authorizers = authorizers;
    }

    @Override
    public String getMatchers() {
        return matchers;
    }

    @Override
    public void setMatchers(String matchers) {
        this.matchers = matchers;
    }

    @Override
    public Boolean getMultiProfile() {
        return multiProfile;
    }

    @Override
    public void setMultiProfile(Boolean multiProfile) {
        this.multiProfile = multiProfile;
    }

    public ServerRedisSessionDao getCDao() {
        return cDao;
    }

    public void setCDao(ServerRedisSessionDao cDao) {
        this.cDao = cDao;
    }
}
