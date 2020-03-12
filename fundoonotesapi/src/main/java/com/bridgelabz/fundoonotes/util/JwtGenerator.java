
package com.bridgelabz.fundoonotes.util;
/**
 * @author shaik shaiksha vali
 * 
 * */
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Component
public class JwtGenerator {
	private static final String SECRET = "1234050365";

	/* Method to generate the token for the particular userId */
	public String jwtToken(long id) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", id).sign(Algorithm.HMAC512(SECRET));
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return token;
	}

	/* Method to decode the token to id */
	public Long parseJWT(String token) {
		Long userId = (long) 0;
		if (token != null) {
			userId = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token).getClaim("id").asLong();
		}
		return userId;
	}

}