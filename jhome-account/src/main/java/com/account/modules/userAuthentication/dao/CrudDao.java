package com.account.modules.userAuthentication.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * @Description 基类接口
 * @Author daxv
 * @Date
 * @Remarks ...
 */
public interface CrudDao {

    /**
     * @Description 单点登录接口
     * @Author daxv
     * @Date
     * @Remarks ...
     */
    <T> ResponseEntity<T> LoginByAccount(String url, HttpMethod method, JSONObject params, Class<T> type);

    /**
     * @Description 退出
     * @Author daxv
     * @Date
     * @Remarks ...
     */
    <T> ResponseEntity<T> LoginOut(String url, JSONObject params, Class<T> type);
}
