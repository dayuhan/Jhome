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
 * @description: 百度翻译
 * @author: Daxv
 * @create: 2021-04-21 10:47
 **/
public class BaiDuTranslate  implements Translate {
   
    @Override
    public String doTranslate(String url, Map<String, Object> paramMap, String regEx, HttpHost proxy) {
        return null;
    }

    @Override
    public String doTranslate(HttpHost proxy) {
        return null;
    }
}
