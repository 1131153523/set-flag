package setflag.utils;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
 
/**
 * APP��¼Token�����ɺͽ���
 * 
 */
public class JwtToken {
 
	/** token��Կ������й¶����������޸� backups:JKKLJOoasdlfj */
	public static final String SECRET = "JKKLJOoasdlfj";
	/** token ����ʱ��: 10�� */
	public static final int calendarField = Calendar.DATE;
	public static final int calendarInterval = 10;
 
	/**
	 * JWT����Token.
	 * 
	 * JWT����: header, payload, signature
	 * 
	 * @param user_id
	 *            ��¼�ɹ����û�user_id, ����user_id���ɴ���
	 */
	public static String createToken(String user_id) throws Exception {
		Date iatDate = new Date();
		// expire time
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(calendarField, calendarInterval);
		Date expiresDate = nowTime.getTime();
 
		// header Map
		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
 
		// build token
		// param backups {iss:Service, aud:APP}
		String token = JWT.create().withHeader(map) // header
				.withClaim("iss", "Service") // payload
				.withClaim("aud", "APP").withClaim("user_id", null == user_id ? null : user_id.toString())
				.withIssuedAt(iatDate) // sign time
				.withExpiresAt(expiresDate) // expire time
				.sign(Algorithm.HMAC256(SECRET)); // signature
 
		return token;
	}
 
	/**
	 * ����Token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Claim> verifyToken(String token) {
		DecodedJWT jwt = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
			jwt = verifier.verify(token);
		} catch (Exception e) {
			// e.printStackTrace();
			// token У��ʧ��, �׳�Token��֤�Ƿ��쳣
		}
		return jwt.getClaims();
	}
 
	/**
	 * ����Token��ȡuser_id
	 * 
	 * @param token
	 * @return user_id
	 */
	public static String getAppUID(String token) {
		Map<String, Claim> claims = verifyToken(token);
		Claim user_id_claim = claims.get("user_id");
		if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
			// token У��ʧ��, �׳�Token��֤�Ƿ��쳣
		}
		return user_id_claim.asString();
	}
	
	
	public static void main(String[] args) {
		try {
			System.out.println(createToken("dwadwadewra"));
			System.out.println(getAppUID("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiZHdhZHdhZGV3cmEiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNTU1ODUyNDIxLCJpYXQiOjE1NTQ5ODg0MjF9.1HzFveQTiPuQC4F5lfyMJk8lIvqrM2ktE1pn3s4s6ns" ));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
