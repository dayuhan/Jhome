package com.ar.common.util;


import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密组件
 *
 * @author xzl
 * @version 1.0
 * @since 1.0
 */
public class MD5Util {

    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD5(String data) throws Exception {

        // 执行消息摘要
        return DigestUtils.md5(data);
    }


    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static String encodeMD5Hex(String data) {
        // 执行消息摘要
        return DigestUtils.md5Hex(data);
    }

    /**
     * MD5 设置长度获取
     * @param plainText
     * @param length
     * @return
     */
    public static String getMd5(String plainText, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                    buf.append(Integer.toHexString(i));
            }
            return buf.toString().substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
}