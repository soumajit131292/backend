package com.techchefs.gmailloginlogout.config;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


@Configuration
public class TokenConfig {
	 TokenConfig() {

	}

	private static final String SECRET = "1234567890ABCDEFGHIJJKLMNOPQRSTUVWXYZ";

	public  String generateJWT(String googleKey) {
		String token = null;
		token = JWT.create().withClaim("GoogleKey", googleKey).sign(Algorithm.HMAC512(SECRET));
		return token;
	}

	public  String parseJWT(String jwt) {
		String GoogleKey = null;
		if (jwt != null) {
			GoogleKey = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwt).getClaim("GoogleKey").asString();
		}
		return GoogleKey;
	}
}
