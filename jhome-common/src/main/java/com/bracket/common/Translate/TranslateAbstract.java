package com.bracket.common.Translate;

import com.bracket.common.ToolKit.StringUtil;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
 * @description: TranslateManagement
 * @author: Daxv
 * @create: 2021-04-21 09:17
 **/
public abstract class TranslateAbstract {
    protected Translate translate;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Map<String, Object> paramMap;
    //代理服务器地址
    protected String proxyUrl = "http://api.wandoudl.com/api/ip?app_key=772e337e6dab6d39415bd637190238fc&pack=0&num=20&xy=1&type=2&lb=&mr=1&area_id=undefined";
    //翻译api地址
    protected String url = "https://translate.google.cn/_/TranslateWebserverUi/data/batchexecute?rpcids=MkEWBc&f.sid=2827523264559919278&bl=boq_translate-webserver_20210420.12_p0&hl=zh-CN&soc-app=1&soc-platform=1&soc-device=1&_reqid=333618&rt=c";
    protected String paramStr = "[[[\"MkEWBc\",\"[[\\\"%s\\\",\\\"%s\\\",\\\"%s\\\",true],[null]]\",null,\"generic\"]]]";
    //代理对象
    protected static List<ProxyModel> proxies = new ArrayList<>();

    /**
     * 执行翻译
     *
     * @param url
     * @param paramMap
     * @param regEx
     * @param
     * @return
     */
    public String doTranslate(String url, Map<String, Object> paramMap, String regEx) {
        String result = "";
        try {
            if (StringUtil.isBlank(url))
                url = this.url;
            if (StringUtil.isBlank(regEx))
                return "";
            //线上翻译
            result = translate.doTranslate(url, paramMap, regEx, getHttpHost(proxies));
            //线下翻译 如果返回结果为空，则执行 本地翻译库
            if (StringUtil.isBlank(result))
                result = translate.doTranslate(getHttpHost(proxies));
        } catch (Exception ex) {
            logger.error(String.format("翻译出错，出错类名:%s,错误原因：%s,", this.getClass().getName(), ex.getMessage()));
        }
        return result;
    }

    /**
     * 获取代理ip
     *
     * @return
     */
    public List<ProxyModel> getProxyIps() {
        List<ProxyModel> proxyList = null;
        if (proxies.size() == 0) {
            try {
                proxyList = ProxyUtil.getProxyIpsByHttp(proxyUrl);
                //获取代理对象列表为空，则执行本地代理
                if (proxyList == null || proxies.size() == 0) {
                    ProxyUtil.getProxyIpsByServer();
                } else {
                    proxies = proxyList;
                }
                return proxies;
            } catch (Exception ex) {
                logger.error(String.format("获取代理服务报错：%s", ex.getMessage()));
            }
        }
        return null;
    }

    //获取代理服务器地址
    public HttpHost getHttpHost(List<ProxyModel> proxies) {
        if (proxies != null && proxies.size() > 0) {
            Random random = new Random();
            int n = random.nextInt(proxies.size());
            ProxyModel proxy = proxies.get(n);
            HttpHost host = new HttpHost(proxy.getIp(), Integer.valueOf(proxy.getPort()));
            return host;
        }
        return null;
    }

    public Translate getTranslate() {
        return translate;
    }

    public void Bind(Translate translate) {
        this.translate = translate;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setTranslate(Translate translate) {
        this.translate = translate;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public static List<ProxyModel> getProxies() {
        return proxies;
    }

    public static void setProxies(List<ProxyModel> proxies) {
        TranslateAbstract.proxies = proxies;
    }
}
