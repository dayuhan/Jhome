package com.bracket.common.Identity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

public class JavaWebToken {

	/**
	 * 创建JWT
	 * 
	 * @param jwt 
	 * @return
	 */
	public static String createJavaWebToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
	}

	private static Key getKeyInstance() {
		// return MacProvider.generateKey();
		// We will sign our JavaWebToken with our ApiKey secret
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("Com.Work");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes,
				signatureAlgorithm.getJcaName());
		return signingKey;
	}
	/**
	 * 解析JWT
	 * 
	 * @param jwt 
	 * @return
	 */
	public static Map<String, Object> verifyJavaWebToken(String jwt) {
		try {

			Map<String, Object> jwtClaims = Jwts.parser()
					.setSigningKey(getKeyInstance()).parseClaimsJws(jwt)
					.getBody();
			return jwtClaims;
		} catch (Exception e) {
			// log.error("json controller token verify failed");
			return null;
		}
	}

	 
	
	/**
	 * 解析JWT
	 * 
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts
					.parser()
					.setSigningKey(
							DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 创建JWT
	 * 
	 * @param no
	 * @param userId
	 * @param issuer
	 * @param TTLMillis
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(String no, String userId, String issuer,
			long TTLMillis, String base64Security) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// 生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter
				.parseBase64Binary(base64Security);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes,
				signatureAlgorithm.getJcaName());
		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
				.claim("no", no).setSubject(userId).setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);
		// 添加Token过期时间
		if (TTLMillis >= 0) {
			long expMillis = nowMillis + TTLMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}
		// 生成JWT
		return builder.compact();
	}

}
