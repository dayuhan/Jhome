package com.ar.common.util;

import java.util.*;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
public class SensitiveWordHelper {

    public static SensitiveWordFilter createSensitiveWordFilter() {
        return new SensitiveWordFilter();
    }

    /**
     * @version 1.0
     * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
     */
    public final static class SensitiveWordInit {

        public HashMap sensitiveWordMap;

        public SensitiveWordInit() {
            super();
        }

        public Map initKeyWord(Set<String> valueOperations) {
            //读取敏感词库
            Set<String> keyWordSet = valueOperations;
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            return sensitiveWordMap;
        }

        public void addSensitiveWordToHashMap(Set<String> keyWordSet) {
            //初始化敏感词容器，减少扩容操作
            sensitiveWordMap = new HashMap(keyWordSet.size());
            String key = null;
            Map nowMap = null;
            Map<String, String> newWorMap = null;
            //迭代keyWordSet
            Iterator<String> iterator = keyWordSet.iterator();
            while (iterator.hasNext()) {
                key = iterator.next();
                nowMap = sensitiveWordMap;
                for (int i = 0; i < key.length(); i++) {
                    char keyChar = key.charAt(i);
                    Object wordMap = nowMap.get(keyChar);
                    //如果存在该key，直接赋值
                    if (wordMap != null) {
                        nowMap = (Map) wordMap;
                        //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    } else {
                        newWorMap = new HashMap<String, String>();
                        //不是最后一个
                        newWorMap.put("isEnd", "0");
                        nowMap.put(keyChar, newWorMap);
                        nowMap = newWorMap;
                    }

                    if (i == key.length() - 1) {
                        //最后一个
                        nowMap.put("isEnd", "1");
                    }
                }
            }
        }
    }

    /**
     * @version 1.0
     * @Description: 敏感词过滤
     */
    public final static class SensitiveWordFilter {
        public Map sensitiveWordMap = null;
        /**
         * 最小匹配规则
         */
        public static int minMatchTYpe = 1;
        /**
         * 最大匹配规则
         */
        public static int maxMatchType = 2;

        private Set<String> valueOperations;

        /**
         * 构造函数
         */
        public SensitiveWordFilter() {
        }

        /**
         * 构造函数，初始化敏感词库
         */
        public SensitiveWordFilter(Set<String> valueOperations) {
            this.sensitiveWordMap = new SensitiveWordInit().initKeyWord(valueOperations);
        }

        /**
         * 判断文字是否包含敏感字符
         *
         * @param txt       文字
         * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
         * @return 若包含返回true，否则返回false
         */
        public boolean isContaintSensitiveWord(String txt, int matchType) {
            boolean flag = false;
            for (int i = 0; i < txt.length(); i++) {
                //判断是否包含敏感字符
                int matchFlag = this.checkSensitiveWord(txt, i, matchType);
                //大于0存在，返回true
                if (matchFlag > 0) {
                    flag = true;
                }
            }
            return flag;
        }

        /**
         * 获取文字中的敏感词
         *
         * @param txt       文字
         * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
         * @return
         */
        public Set<String> getSensitiveWord(String txt, int matchType) {
            Set<String> sensitiveWordList = new HashSet<String>();
            for (int i = 0; i < txt.length(); i++) {
                //判断是否包含敏感字符
                int length = checkSensitiveWord(txt, i, matchType);
                //存在,加入list中
                if (length > 0) {
                    sensitiveWordList.add(txt.substring(i, i + length));
                    //减1的原因，是因为for会自增
                    i = i + length - 1;
                }
            }
            return sensitiveWordList;
        }

        /**
         * 替换敏感字字符
         *
         * @param txt
         * @param matchType
         * @param replaceChar 替换字符，默认*
         */
        public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
            String resultTxt = txt;
            //获取所有的敏感词
            Set<String> set = getSensitiveWord(txt, matchType);
            Iterator<String> iterator = set.iterator();
            String word = null;
            String replaceString = null;
            while (iterator.hasNext()) {
                word = iterator.next();
                replaceString = getReplaceChars(replaceChar, word.length());
                resultTxt = resultTxt.replaceAll(word, replaceString);
            }
            return resultTxt;
        }

        /**
         * 获取替换字符串
         *
         * @param replaceChar
         * @param length
         * @return
         */
        private String getReplaceChars(String replaceChar, int length) {
            String resultReplace = replaceChar;
            for (int i = 1; i < length; i++) {
                resultReplace += replaceChar;
            }
            return resultReplace;
        }

        /**
         * 检查文字中是否包含敏感字符，检查规则如下：<br>
         *
         * @param txt
         * @param beginIndex
         * @param matchType
         * @return，如果存在，则返回敏感词字符的长度，不存在返回0
         */
        public int checkSensitiveWord(String txt, int beginIndex, int matchType) {
            //敏感词结束标识位：用于敏感词只有1位的情况
            boolean flag = false;
            //匹配标识数默认为0
            int matchFlag = 0;
            char word = 0;
            Map nowMap = this.sensitiveWordMap;
            for (int i = beginIndex; i < txt.length(); i++) {
                word = txt.charAt(i);
                nowMap = (Map) nowMap.get(word);
                //存在，则判断是否为最后一个
                if (nowMap != null) {
                    //找到相应key，匹配标识+1
                    matchFlag++;
                    //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    if ("1".equals(nowMap.get("isEnd"))) {
                        //结束标志位为true
                        flag = true;
                        //最小规则，直接返回,最大规则还需继续查找
                        if (SensitiveWordFilter.minMatchTYpe == matchType) {
                            break;
                        }
                    }
                    //不存在，直接返回
                } else {
                    break;
                }
            }
            //长度必须大于等于1，为词
            if (matchFlag < 2 || !flag) {
                matchFlag = 0;
            }
            return matchFlag;
        }

        public Set<String> setValueOperationsgetValueOperations() {
            return valueOperations;
        }

        public void setValueOperations(Set<String> valueOperations) {
            this.valueOperations = valueOperations;
            this.sensitiveWordMap = new SensitiveWordInit().initKeyWord(valueOperations);
        }
    }
}
