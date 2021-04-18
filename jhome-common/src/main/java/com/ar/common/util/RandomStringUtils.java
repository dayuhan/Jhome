package com.ar.common.util;

import java.util.Random;

/**
 * CODE生成随机数（不能保证数字唯一）
 * @author Levi_liu
 */
public class RandomStringUtils {

    public static String getRandomNum(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(random.nextInt(10)));
        }
        return sb.toString();
    }

    /**
     * 生成随机数字和字母,
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            sb.append(genRandomStr(random));
        }
        return sb.toString();
    }

    public static String getRandomString(int length, String[] removeConfusingChars) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            //输出字母还是数字
            String temp = genRandomStr(random);
            if(removeConfusingChars != null){
                while(existsConfusingChars(temp,removeConfusingChars)){
                    temp = genRandomStr(random);
                }
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    private static String genRandomStr(Random random){
        if (random.nextInt(2) % 2 == 0) {
            //输出是大写字母还是小写字母
            int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
            return String.valueOf((char) (random.nextInt(26) + temp));
        } else {
            return String.valueOf(random.nextInt(10));
        }
    }

    private static boolean existsConfusingChars(String source, String[] removeConfusingChars){
        for(String s:removeConfusingChars){
            if(s.equalsIgnoreCase(source)){
                return true;
            }
        }
        return false;
    }
}
