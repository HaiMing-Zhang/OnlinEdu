package com.guli.ucenter.utils;

import com.guli.ucenter.entity.EduUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author helen
 * @since 
 */
public class JwtUtils {

	public static final String SUBJECT = "guli-user";

	//秘钥
	public static final String APPSECRET = "guli666";

	//过期时间，毫秒，30分钟
	public static final long EXPIRE = 1000 * 60 * 1;

	/**
	 * 生成Jwt令牌
	 * @param eduUser
	 * @return
	 */
	public static String generateJWT(EduUser eduUser){

		if(eduUser == null
				|| StringUtils.isEmpty(eduUser.getId())
				|| StringUtils.isEmpty(eduUser.getNickname())
				|| StringUtils.isEmpty(eduUser.getAvatar())){
			return null;
		}

		String token = Jwts.builder()
				.setSubject(SUBJECT)
				.claim("id", eduUser.getId())
				.claim("nickname", eduUser.getNickname())
				.claim("avatar", eduUser.getAvatar())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				.signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

		return token;
	}


	/**
	 * 校验jwt
	 * @param jwtToken
	 * @return
	 */
	public static Claims checkJWT(String jwtToken){

		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(jwtToken);
		Claims claims = claimsJws.getBody();

		return claims;
	}
}
