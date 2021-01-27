package com.bracket.common.ToolKit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判断字符串是空
     *
     * @param currentStr
     * @return
     */
    public static boolean isBlank(String currentStr) {
        if (currentStr == null || currentStr.isEmpty() || currentStr.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串 不为空
     *
     * @param currentStr
     * @return
     */
    public static boolean isNotBlank(String currentStr) {
        if (currentStr != null && !currentStr.isEmpty() && !currentStr.equals("")) {
            return true;
        }
        return false;
    }
    /**
     * 生成指定位数的随机数
     *
     * @param length
     * @return
     */
    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * 生成随机字符串
     *
     * @param   len 邀请码长度
     */
    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "23456789abcdefghgklmnpqrstuvwxyz";//去掉1和i ，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }

    /**
     * 把string array or list用给定的符号symbol连接成一个字符串
     *
     * @param list   需要处理的列表
     * @param symbol 链接的符号
     * @return 处理后的字符串
     */
    public static String joinString(List list, String symbol) {
        String result = "";
        if (list != null) {
            for (Object o : list) {
                String temp = o.toString();
                if (temp.trim().length() > 0)
                    result += (temp + symbol);
            }
            if (result.length() > 1) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    /**
     * 截取字符串左侧的Num位截取到末尾
     *
     * @param str1 处理的字符串
     * @param num  开始位置
     * @return 截取后的字符串
     */
    public static String ltrim(String str1, int num) {
        String tt = "";
        if (!isEmpty(str1) && str1.length() >= num) {
            tt = str1.substring(num, str1.length());
        }
        return tt;

    }

    /**
     * 截取字符串右侧第0位到第Num位
     *
     * @param str 处理的字符串
     * @param num 截取的位置
     * @return 截取后的字符串
     */
    public static String rtrim(String str, int num) {
        if (!isEmpty(str) && str.length() > num) {
            str = str.substring(0, str.length() - num);
        }
        return str;
    }

    /**
     * 截取字符串左侧指定长度的字符串
     *
     * @param input 输入字符串
     * @param count 截取长度
     * @return 截取字符串
     */
    public static String left(String input, int count) {
        if (isEmpty(input)) {
            return "";
        }
        count = (count > input.length()) ? input.length() : count;
        return input.substring(0, count);
    }

    /**
     * 截取字符串右侧指定长度的字符串
     *
     * @param input 输入字符串
     * @param count 截取长度
     * @return 截取字符串
     * Summary 其他编码的有待测试
     */
    public static String right(String input, int count) {
        if (isEmpty(input)) {
            return "";
        }
        count = (count > input.length()) ? input.length() : count;
        return input.substring(input.length() - count, input.length());
    }

    private static boolean isEmpty(String input) {
        if (input == null || input.isEmpty() || input.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param str 需要处理的字符串
     */
    public static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /**
     * 把字符串中的数字替换成指定符号
     *
     * @param str
     * @param replace
     * @return
     */
    public static String replaceAllNum(String str, String replace) {
        if (str != null) {
            str = str.replaceAll("\\d+", replace);
        }
        return str;
    }

    /**
     * 把字符串中的指定符号替换成指定符号
     *
     * @param str
     * @param symbol
     * @param replace
     * @return
     */
    public static String replaceAllDesignatedSymbol(String str, String symbol, String replace) {
        if (str != null) {
            str = str.replaceAll(symbol, replace);
        }
        return str;
    }
}
