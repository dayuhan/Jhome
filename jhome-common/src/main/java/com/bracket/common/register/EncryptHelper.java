package com.bracket.common.register;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Random;

public final class EncryptHelper {
	/*
	 * public static String MD5Encrypt16(String str) { MD5CryptoServiceProvider
	 * provider = new MD5CryptoServiceProvider(); return
	 * BitConverter.toString(provider.ComputeHash(str.getBytes()), 4,
	 * 8).replace("-", ""); }
	 */

	/**
	 * @param str
	 * @return
	 */
	public static String MD5Encrypt32(String str) {
		String str2 = MD5.Create().ComputeHash(str.getBytes());
		return str2.toLowerCase();
	}

	/**
	 * AES����
	 * 
	 * @param text
	 * @param password
	 * @return
	 */
	public static String AESDecrypt(String text, String password) {
		try {
			if (text == null || password == null)
				return null;
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] bs = password.getBytes("utf-8");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(bs, "AES"));
			byte[] bytes = new BASE64Decoder().decodeBuffer(text);
			bytes = cipher.doFinal(bytes);
			return new String(bytes, "utf-8");
		} catch (Exception e) {
			return text;
		}
	}

	/**
	 * AES����
	 *
	 * @param text
	 * @param password
	 * @return
	 */
	public static String AESEncrypt(String text, String password) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] bs = password.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(bs, "AES"));
			byte[] bytes = cipher.doFinal(text.getBytes("utf-8"));
			return new BASE64Encoder().encode(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return text;
		}
	}

	/**
	 * 随机生成密钥
	 * 
	 * @return
	 */
	public static String GetIv(int n) {
		char[] arrChar = new char[] { 'a', 'b', 'd', 'c', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 'q', 's', 't', 'u',
				'v', 'w', 'z', 'y', 'x', '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'Q', 'P', 'R', 'T', 'S', 'V', 'U',
				'W', 'X', 'Y', 'Z' };
		StringBuilder num = new StringBuilder();
		Random rnd = new Random(new Date().getTime());
		for (int i = 0; i < n; i++) {
			num.append(String.valueOf(arrChar[rnd.nextInt(arrChar.length)]));
		}
		return num.toString();
	}
}