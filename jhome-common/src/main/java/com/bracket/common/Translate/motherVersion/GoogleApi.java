/*
package com.bracket.common.Translate;

import com.alibaba.fastjson.JSONArray;
import com.other.common.lang.StringUtils;


import java.io.*;
import java.net.URLEncoder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


import com.alibaba.fastjson.JSONArray;

*/
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
 * 1 获取 tkk
 * 2 根据 tkk，和 输入内容 获取 tk
 * 3 根据 word,tk ,组装 url 访问 谷歌翻译 api
 * @program: jhome-root
 * @description: google翻译
 * @author: Daxv
 * @create: 2021-04-19 16:47
 **//*

public class GoogleApi {
    private static final String PATH ="/gettk.js";
    static ScriptEngine engine = null;
    public Browser browser = null;

    static {
        ScriptEngineManager maneger = new ScriptEngineManager();
        engine = maneger.getEngineByName("javascript");
        FileInputStream fileInputStream = null;
        Reader scriptReader = null;
        try {
            InputStream inputStream= GoogleApi.class.getResourceAsStream(PATH);
            scriptReader = new InputStreamReader(inputStream, "utf-8");
            engine.eval(scriptReader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (scriptReader != null) {
                try {
                    scriptReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public GoogleApi() {
        this.browser = new Browser();
    }

    public GoogleApi(String ip, Integer port) {
        this.browser = new Browser();
        this.browser.setUrl(ip);
        this.browser.setPort(port);
    }

    public String getTKK() {
        browser.setUrl("https://translate.google.cn/");
        try {
            String result = browser.executeGet();

            if (StringUtils.isNotBlank(result)) {
                if (result.indexOf("TKK") > -1) {
                    String tkk = result.split("TKK")[1];
                    tkk = tkk.split("\\)\\;")[0];
                    tkk = tkk + ");";
                    tkk = tkk.substring(1, tkk.length());
                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptEngine engine = manager.getEngineByName("javascript");
                    return (String) engine.eval(tkk);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "434673.1650587100";
    }

    public static String getTK(String word, String tkk) {
        String result = null;
        try {
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                result = (String) invocable.invokeFunction("tk", new Object[]{word, tkk});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String translate(String word, String from, String to) {
        if (StringUtils.isBlank(word)) {
            return null;
        }
        String _tkk = getTKK();
        if (StringUtils.isBlank(_tkk)) {
            return null;
        }
        String _tk = getTK(word, _tkk);
        _tk="434673.1650587100";
        try {
            word = URLEncoder.encode(word, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer("https://translate.google.cn/translate_a/single?client=t");
        buffer.append("&sl=" + from);
        buffer.append("&tl=" + to);
        buffer.append("&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&kc=0");
        buffer.append("&tk=" + _tk);
        buffer.append("&q=" + word);
        browser.setUrl(buffer.toString());
        try {
            String result = browser.executeGet();
            JSONArray array = (JSONArray) JSONArray.parse(result);
            JSONArray r_array = array.getJSONArray(0);
            StringBuffer r_buffer = new StringBuffer();
            for (int i = 0; i < r_array.size(); i++) {
                String _r = r_array.getJSONArray(i).getString(0);
                if (StringUtils.isNotBlank(_r)) {
                    r_buffer.append(_r);
                }
            }
            return r_buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        GoogleApi googleApi = new GoogleApi();
        //GoogleApi googleApi = new GoogleApi("122.224.227.202", 3128);
        String result = googleApi.translate("Many applications within the enterprise domain ", "", "zh");
        System.out.println("-------------------------------------------");
        System.out.println(result);
    }
}
*/
