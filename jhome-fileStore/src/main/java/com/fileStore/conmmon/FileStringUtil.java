package com.fileStore.conmmon;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.other.common.lang.StringUtils;
import com.twelvemonkeys.lang.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @program: jhome-root
 * @description:
 * @author: Daxv
 * @create: 2020-09-12 13:16
 **/
public class FileStringUtil{
        private static final String ranChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public final String regularLoginName = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,50}$";

        public final String regularMobile = "^[1][3,4,5,7,8][0-9]{9}$";

        public final String regularRole = "^.{1,20}$";

        public static boolean isEmpty(Object obj) {
            if (obj == null) {
                return true;
            }
            if (obj instanceof CharSequence) {
                return ((CharSequence) obj).length() == 0;
            }
            if (obj instanceof Collection) {
                return ((Collection) obj).isEmpty();
            }
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            if (obj instanceof Object[]) {
                Object[] object = (Object[]) obj;
                if (object.length == 0) {
                    return true;
                }
                boolean empty = true;
                for (int i = 0; i < object.length; i++) {
                    if (!isEmpty(object[i])) {
                        empty = false;
                        break;
                    }
                }
                return empty;
            }
            return false;
        }

        public static boolean isNotEmpty(Object obj) {
            return !isEmpty(obj);
        }


        /**
         * 将逗号隔开的字符串转换
         *
         * @param strs
         * @return
         */
        public static List<String> changeStringList(String strs) {
            final String comma = ",";
            final String empty = " ";
            List<String> list = Lists.newArrayList();

            String regex = ",|，|\\s+";
            if (strs.contains(comma) || strs.contains(comma) || strs.contains(empty)) {
                list = Arrays.asList(strs.split(regex));
            } else {
                list.add(strs);
            }
            return list;
        }

        public static List<Integer> changeIntegerList(String ids) {
            List<Integer> list = Lists.newArrayList();

            if (ids.contains(",")) {
                String[] idList = ids.split(",");
                for (String id : idList) {
                    list.add(Integer.parseInt(id));
                }
            } else {
                list.add(Integer.parseInt(ids));
            }
            return list;
        }

        /**
         * 去掉全角的空格
         * <br/><方法概述>
         * <br/><方法详细概述>
         *
         * @param string
         * @return
         */
        public static String trim(String string) {
            if (string == null || string.length() == 0) {
                return "";
            }
            String str = string.trim();
            while (str.startsWith("　") || str.startsWith(" ")) {
                str = str.substring(1);
            }
            while (str.endsWith("　") || str.endsWith(" ")) {
                str = str.substring(0, str.length() - 1);
            }
            return str;
        }

        public static String isNullChangeToDefault(String old, String defaultStr) {
            defaultStr = old == null || "null".equals(old) || "".equals(old.trim()) ? defaultStr : old;
            // 如果旧字符串等于null则用newStr代替返回
            return defaultStr;
        }

        public static String productAutoNum() {// 产生record中的autonum
            Calendar cd = Calendar.getInstance();
            return "" + cd.get(Calendar.YEAR) + (cd.get(Calendar.MONTH) + 1)
                    + cd.get(Calendar.DATE) + cd.get(Calendar.HOUR);
        }


        public static String formatNumber(String num, int n)// 保留n位小数
        {
            if (isEmpty(num)) {
                return "0";
            }
            try {
                float number = Float.parseFloat(num);

                return formatNumber(number, n);
            } catch (NumberFormatException ne) {
                return "0";
            }
        }

        /**
         * 格式化数字，保留n位小数
         *
         * @param num
         * @param n
         * @return
         */
        public static String formatNumber(double num, int n) {
            if (num == 0f) {
                return num + "";
            }
            StringBuffer str = new StringBuffer("#.");
            for (int i = 0; i < n; i++) {
                str.append("#");
            }
            DecimalFormat d = new DecimalFormat(str.toString());
            StringBuffer stb = new StringBuffer("");
            d.format(num, stb, new FieldPosition(n));
            return stb.toString();
        }


        /**
         * 判断是否为数字
         *
         * @param number
         * @return
         */
        public static boolean isNumber(String number) {
            try {
                Float.parseFloat(number);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static Integer parseInt(String num) {
            if (num == null) {
                return -1;
            }
            int in = 0;
            try {
                in = Integer.parseInt(num);
                return in;
            } catch (Exception e) {
                String myInt = "";
                char ch[] = num.toCharArray();
                for (int i = 0; i < ch.length; i++) {
                    if (ch[i] >= 48 && ch[i] <= 58) {
                        myInt += ch[i];
                    } else if (!"".equals(myInt)) {
                        break;
                    }
                }
                try {
                    int n = Integer.parseInt(myInt);
                    if (num.startsWith("-")) {
                        n = 0 - n;
                    }
                    return n;
                } catch (Exception ee) {
                    return -1;
                }
            }
        }

        /**
         * 将字符串转换成指定长度，超长的用．．表示
         *
         * @param str
         * @param i
         * @return
         */
        public static String toLength(String str, int i) {

            if (str != null && str.length() > i) {
                str = str.substring(0, i - 2) + "..";
            }
            return str;
        }

        /**
         * <方法概述>判断不是数字
         * <方法详细概述>
         * <版本>
         *
         * @param number
         * @return
         */
        public static boolean isNotNumber(String number) {
            return !isNumber(number);
        }

        /**
         * 将list转换成字符串,且用split联接
         *
         * @param ids
         * @param split
         * @return
         */
        public static String toStringFromList(List<?> ids, String split) {
            if (ids == null || ids.size() == 0) {
                return null;
            } else {
                StringBuilder stb = new StringBuilder();
                for (Object obj : ids) {
                    stb.append(obj).append(split);
                }
                if (stb.toString().endsWith(split)) {
                    return stb.substring(0, stb.length() - split.length());
                } else {
                    return stb.toString();
                }
            }
        }

        public static boolean isBlank(String str) {
            int strLen;
            if (str == null || (strLen = str.length()) == 0) {
                return true;
            }
            for (int i = 0; i < strLen; i++) {
                if ((Character.isWhitespace(str.charAt(i)) == false)) {
                    return false;
                }
            }
            return true;
        }

        public static boolean isNotBlank(String str) {
            return !com.bracket.common.ToolKit.StringUtil.isBlank(str);
        }

        /**
         * 生成特定长度的随机数字和字母（大写）组合
         *
         * @param len 需要生成的随机数的长度
         * @return 返回特定长度的String类型
         * @author luofr
         */
        public static String getRandomNumLetter(int len) {
            Random ran = new Random();
            String ret = "";
            for (int i = 0; i < len; i++) {
                ret += ranChar.charAt(ran.nextInt(ranChar.length()));
            }
            return ret;
        }

        /**
         * 获取文件路径中的文件名称
         * * @param path
         *
         * @return
         */
        public static String getFileNameByPath(String path) {
            if (Strings.isNullOrEmpty(path)) {
                return "";
            } else if (path.lastIndexOf(File.separator) > -1) {
                return path.substring(path.lastIndexOf(File.separator) + 1);
            } else {
                return path;
            }
        }


        /**
         * 获取文件名，不带扩展名
         * * @param path
         *
         * @return
         */
        public static String getNameWithoutExtension(String path) {
            if (Strings.isNullOrEmpty(path)) {
                return path;
            }
            String fileName = getFileNameByPath(path);
            int dotIndex = fileName.lastIndexOf(46);
            return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
        }

        /**
         * 获取List 转Long[]
         * * @param path
         *
         * @return
         */
        public static Long[] arrayListToLong(ArrayList<Long> arrayList) {
            if (arrayList == null || arrayList.size() == 0) {
                return null;
            }
            Long[] str = new Long[arrayList.size()];
            int i = 0;
            for (Long value : arrayList) {
                str[i++] = value;
            }
            return str;
        }

        /**
         * 模拟mysql
         * substring_index(str,delim,count)
         * <p>
         * str:要处理的字符串
         * delim:分隔符
         * count:计数 字符串用分隔符分隔后形成字符串数组
         *
         * @param targetStr 目标字符串
         * @param str       查找字符串
         * @param index     第n次出现
         * @return
         */
        public static String substringIndex(String targetStr, String str, int index) {
            /**
             * 当 str 不存在于 targetStr 时，不管是正序还是反序都返回原字符串
             * 当index大于 str 在  targetStr 中出现的次数，不管是正序还是反序都返回原字符串
             */
            String result = targetStr;//默认返回字符串为原字符串
            if (targetStr == null || targetStr.trim().length() == 0) {
                return result;
            }
            //当index=0时，返回空
            if (index == 0) {
                return "";
            }
            int beginIndex = 0;//用于匹配字符串的起始位置
            int count = 0; //记录字符串出现的次数
            while ((beginIndex = targetStr.indexOf(str, beginIndex)) != -1) {
                count++;
                //当index与字符串出现次数相同时，开始返回结果
                if (count == index) {
                    return targetStr.substring(0, beginIndex);
                }
                beginIndex = beginIndex + str.length();//更改匹配字符串的起始位置
            }
            return result;
        }


        public static String getHostAddress() {
            InetAddress address = null;
            try {
                address = InetAddress.getLocalHost();
                return address.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return "192.168.1.1";
        }

        /**
         * 截断字符串
         *
         * @return
         */
        public static String truncationStr(String str, int maxLen) {
            if (StringUtils.isBlank(str)) {
                return str;
            } else if (str.length() > maxLen) {
                return str.substring(0, maxLen - 1);
            } else {
                return str;
            }

        }

        /**
         * 密码加盐加密
         *
         * @param password
         * @param salt
         * @return
         */
        public static String hashPassword(String password, String salt) {
            return DigestUtils.sha256Hex(password + salt);
        }

        /**
         * 按
         *
         * @param a
         * @param b
         * @return
         */
        public static String divideToStringScale1(int a, int b) {
            return new BigDecimal((float) a / b).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 生成uuid
         *
         * @return
         */
        public static String getUUID() {
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString();
            String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);//015ef458d2744ac2a32d19cc9c079631
            return temp;
        }

        public static void main(String[] args) throws Exception {
            //生成新的盐值
//            String salt = RandomStringUtils.getRandomString(10);
//            System.out.println("salt:" + salt);
//            System.out.println(hashPassword("000000", "5272eQ6Y37"));
        }

        //效验
        public static boolean sqlValidate(String str) {
            str = str.toLowerCase();//统一转为小写
            String badStr = "'|select|update|and|or|delete|insert|truncate|char|into"
                    + "|substr|declare|exec|master|drop|execute|"
                    + "union|;|--|+|,|like|//|/|%|#|*|$|@|\"|http|cr|lf|<|>|(|)";//过滤掉的sql关键字，可以手动添加
            String[] badStrs = badStr.split("\\|");
            for (int i = 0; i < badStrs.length; i++) {
                if (str.indexOf(badStrs[i]) >= 0) {
                    return false;
                }
            }
            return true;
        }
}
