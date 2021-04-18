package com.account.common.sbUtil.util;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

import java.io.IOException;

public class UserAgentUtil {
    /**
     * 初始化UASparser
     */
    private static UASparser uasParser = null;
    public static UASparser getUasParser(){
        if(uasParser==null){
            try {
                uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return uasParser;
    }

    /**
     * 是否移动设备
     * @param agent
     * @return
     */
    public static boolean CheckAgent(String agent) {
        boolean flag = false;
        String[] keywords = { "Android", "iPhone", "iPod", "iPad",
                "Windows Phone", "MQQBrowser" };
        // 排除 Windows 桌面系统
        if (!agent.contains("Windows NT")
                || (agent.contains("Windows NT") && agent
                .contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!agent.contains("Windows NT") && !agent.contains("Macintosh")) {
                for (String item : keywords) {
                    if (agent.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }

        return flag;
    }

    public static void main(String[] args) {
        // String str =
        // "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36";
        // String
        // str="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        //String str = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";
        String str="";
        System.out.println(str);
        try {
            UserAgentInfo userAgentInfo = UserAgentUtil.uasParser.parse(str);
            System.out.println("操作系统名称：" + userAgentInfo.getOsFamily());
            System.out.println("操作系统：" + userAgentInfo.getOsName());
            System.out.println("浏览器名称：" + userAgentInfo.getUaFamily());
            System.out.println("浏览器版本：" + userAgentInfo.getBrowserVersionInfo());
            System.out.println("设备类型：" + userAgentInfo.getDeviceType());
            System.out.println("浏览器:" + userAgentInfo.getUaName());
            System.out.println("类型：" + userAgentInfo.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
