package com.bracket.common.Translate;

import com.bracket.common.ToolKit.StringUtil;
import com.bracket.common.Translate.motherVersion.GoogleTranslateApi;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
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
 * @description: 翻译
 * @author: Daxv
 * @create: 2021-04-21 09:41
 **/
public class TranslateCommander extends TranslateAbstract {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public TranslateCommander(Translate translate) {
        this.paramMap = new HashMap<>();
        super.translate = translate;
        //初始化代理
        if (super.getProxies() == null || super.getProxies().size() == 0)
        {
            //super.getProxyIps();
        }
    }

    /**
     * 翻译
     *
     * @param word 翻译文字
     * @param from 来自语言
     * @param to   转换后的语言
     * @param regEx  正则表达式
     * @return
     */
    public String TranslateInvoke(String word, String from, String to, String regEx) {
        String result = "";
        try {
            clear();
            if (StringUtil.isBlank(regEx))
                regEx = "[\\u4e00-\\u9fa5]+[^,~！@#￥%……&*()——+{}][\\u4e00-\\u9fa5]+";
            paramMap.put("f.req", String.format(paramStr, word, StringUtil.isNotBlank(from) ? from : "auto", StringUtil.isNotBlank(to) ? to : "zh-CN"));
            result = super.doTranslate(url, paramMap, regEx);
            logger.info("***************翻译结果******************");
            logger.info(result);
            logger.info("****************************************");
        } catch (Exception ex) {
            logger.error(String.format("错误类：%s，错误原因：%s", this.getClass().getName(), ex.getMessage()));
        }
        return result;
    }

    public void clear() {
        if (paramMap.size() > 0)
            paramMap.clear();
    }

    public static void main(String[] args) throws InterruptedException {
        TranslateCommander translateCommander = new TranslateCommander(new GoogleTranslate());
//        translateCommander.TranslateInvoke("난 당신이 너무 좋아", "", "");
//        Thread.sleep(1000);
        translateCommander.TranslateInvoke("Jianke Building", "", "",null);

    }

}
