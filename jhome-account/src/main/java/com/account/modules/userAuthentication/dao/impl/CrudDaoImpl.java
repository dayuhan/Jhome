package com.account.modules.userAuthentication.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.bracket.common.Bus.AbstractDaoImpl.HttpDaoImpl;
import com.account.modules.userAuthentication.dao.CrudDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * @program: account-root
 * @description
 * @author: Daxv
 * @create: 2020-06-13 17:42
 **/
public class CrudDaoImpl extends HttpDaoImpl implements CrudDao {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RestTemplate client;

    /**
     * 参数传递
     * JSONObject jsonObj = new JSONObject();
     * jsonObj.put("appID", ucLoginReq.getAppID());
     * jsonObj.put("loginName", ucLoginReq.getLoginName());
     * jsonObj.put("pwd", ucLoginReq.getPwd());
     * jsonObj.put("signature", ucLoginReq.getSignature());
     * jsonObj.put("timestamp", ucLoginReq.getTimestamp().toString());
     *
     * ResponseEntity<String> resultVo = ucService.loginByAccount(ucApiConfigProperties.getLoginActionPath(), HttpMethod.POST, jsonObj, String.class);
     * JSONObject object= (JSONObject) JSONObject.parse(resultVo.getBody());
     *
     * if (resultVo.getStatusCodeValue() == 200&&object.getString("returnCode").equals("200")) {
     *      UserInfo userInfo=this.resolveUser(object);
     *      return new SimpleAuthenticationInfo(userInfo.toString(), pws, this.getName());
     * } else {
     *      logger.info(String.format("UC单点登陆失败，失败原因：%s", resultVo.getBody().toString()));
     * }
     *
     * @param url
     * @param params
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T> ResponseEntity<T> LoginByAccount(String url, HttpMethod method, JSONObject params, Class<T> type) {
        try {
            return super.SendByPostRequest(url, method, params, type, client);
        } catch (Exception ex) {
            logger.error(String.format("单点登录失败：%s", ex.getMessage()));
            return null;
        }
    }

    @Override
    public <T> ResponseEntity<T> LoginOut(String url, JSONObject params, Class<T> type) {
        try {
            return super.SendByGetRequest(url, params, type, client);
        } catch (Exception ex) {
            logger.error(String.format("注销失败：%s", ex.getMessage()));
            return null;
        }
    }
}
