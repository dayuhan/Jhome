package com.ar.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSAEncryptUtils {

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        //单机环境公钥
        String publicKeyStringSingle = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDC5+VIx0VJaVGrSowExaDaVEoT\n" +
                "8PZ5WkZkaIKcniHUbV+oZ3hOU9iZF/ieWGFH3PSfHC/lfydQkq50aGTHMCAM0sDl\n" +
                "2oZ+Rdi6JHwkv4P1L/5jgtRqDEm5drVxCHHxuZ26HahNU9pCw/Ubh46M+G3uj1sD\n" +
                "EmrW8peWQiUFIB8kwQIDAQAB";
        //本地环境公钥
        String publicKeyStringLocal = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMLn5UjHRUlpUatK\n" +
                "                jATFoNpUShPw9nlaRmRogpyeIdRtX6hneE5T2JkX+J5YYUfc9J8cL+V/J1CSrnRo\n" +
                "                ZMcwIAzSwOXahn5F2LokfCS/g/Uv/mOC1GoMSbl2tXEIcfG5nbodqE1T2kLD9RuH\n" +
                "                joz4be6PWwMSatbyl5ZCJQUgHyTBAgMBAAECgYBh9oR77AybNWBjI/j0fRC4LB1/\n" +
                "                oGGB5Sn0VcMwTRrb1o1562fAlswLRs0jaXCb2ibpwhUg2KQ+Dzu4LrBYnYu9hJd+\n" +
                "                d/bj38LigpfPhuXZT69LYyv75DGXexlqc/I8EkI0BmWOAXfWfAemWFfxYZdy1ujq\n" +
                "                kXI2e/9zX9SEB538QQJBAPbCUep++rvS60XnXIYWPJFaycqDFikEit2nG5DrP0cT\n" +
                "                qOudrS2KPRLo0hhnBwNUhhCVQbinBmXB7/s6nvngqC0CQQDKNHVqILn7j7kBAeFi\n" +
                "                NC2GetpUkKCzsiTUzEMla6bBQ7skebIYJ1xsw8YfndOJx/UVJC+BBYBsFclZOgNy\n" +
                "                m9dlAkEAqL+G6Yhgy6WYA7wOyBvY28ZsjTNMKkzS8nXfIiHeqirFsCuqTKRm09Go\n" +
                "                K8v5PcvFyv1nbnG6rEgOZ45VPajnGQJAG23GR6Es2V0DoYlZw3KEgmuD1ljQE9Ak\n" +
                "                guE6cocJ1cmURzxi1jfwp6he5ccI/PWs3zNLZlsSspa5+2qNMSzGqQJBAMmbVFqe\n" +
                "                JdGW+sHUDam3jDqlRm5/0RTslIVZB8OI6bKh8Cl5QMnAiomlsfWIpZ74sDrGnvUd\n" +
                "                C9F1eRM1KUBBIDo=";
        //加密字符串
        String message = "123456";
        String messageEn = encrypt(message, publicKeyStringSingle);
        System.out.println(message + "\t加密后的字符串为1:" + messageEn);
		//String messageDe = decrypt(messageEn, privateKeyString);
		//System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        System.out.println("publicKeyString:" + publicKeyString);
        System.out.println("privateKeyString:" + privateKeyString);
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) {
        //base64编码的公钥
        try {
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("rsa encrypt err ");
        }

    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (Exception e) {
            throw new RuntimeException("rsa encrypt err ");
        }
    }

}
