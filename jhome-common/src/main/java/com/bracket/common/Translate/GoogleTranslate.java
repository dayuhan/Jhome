package com.bracket.common.Translate;

import org.apache.http.HttpHost;

import java.util.Map;

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
 * @description: google翻译
 * @author: Daxv
 * @create: 2021-04-21 09:15
 **/
public class GoogleTranslate implements Translate {
    protected Map<String, Object> paramMap;
    protected String url = "https://translate.google.cn/_/TranslateWebserverUi/data/batchexecute?rpcids=MkEWBc&f.sid=6739914524248906935&bl=boq_translate-webserver_20210418.17_p0&hl=zh-CN&soc-app=1&soc-platform=1&soc-device=1&_reqid=258052&rt=c";
    protected String paramStr = "[[[\"MkEWBc\",\"[[\\\"%s\\\",\\\"auto\\\",\\\"zh-CN\\\",true],[null]]\",null,\"generic\"]]]";

    @Override
    public String doTranslate(String url, Map<String, Object> paramMap, String regEx, HttpHost proxy) {

        proxy=new HttpHost("114.100.2.35",23564,"https");
        return TranslateHttpUtil.postData(url,paramMap,regEx,proxy);
    }

    @Override
    public String doTranslate(HttpHost proxy) {
        return null;
    }
}
