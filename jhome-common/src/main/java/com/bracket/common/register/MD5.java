package com.bracket.common.register;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class MD5 {

	public static MD5 Create() {
		// TODO Auto-generated method stub
		return new MD5();
	}

	public String ComputeHash(byte[] bytes) {
		MessageDigest digest;
		StringBuffer hasHexString;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(bytes, 0, bytes.length);
			byte messageDigest[] = digest.digest();
			hasHexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hasHexString.append('0');
				hasHexString.append(hex);
			}
			return hasHexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
