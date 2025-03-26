package com.enotes.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.enotes.exception.JwtTokenExpiredException;
import com.enotes.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

	public String secretKey = "";

	public JwtServiceImpl() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (Exception e) {
			log.error("Error at the time of secret key generation", e);
			e.printStackTrace();
		}
	}

	@Override
	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());

		String token = Jwts.builder().claims(claims).subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000)) // 1 Hour
				.signWith(getKey()).compact();

		return token;
	}

	private Key getKey() {
		byte[] decode = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(decode);
	}

	@Override
	public String extractUsername(String jwtToken) {
		Claims claims = extractAllClaims(jwtToken);
		return claims.getSubject();
	}

	private Claims extractAllClaims(String jwtToken) {
		try {
			return Jwts.parser().verifyWith(decryptKey(secretKey)).build().parseSignedClaims(jwtToken).getPayload();

		} catch (ExpiredJwtException e) {
			throw new JwtTokenExpiredException("token is expired !");
		} catch (JwtException e) {
			throw new JwtTokenExpiredException("invalid jwt token");
		} catch (Exception e) {
			throw e;
		}
	}

	private SecretKey decryptKey(String secretKey) {
		byte[] decode = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(decode);
	}

	@Override
	public Boolean validateToken(String jwtToken, UserDetails details) {
		String username = extractUsername(jwtToken);
		Boolean isExpired = isTokenExpired(jwtToken);
		if (username.equalsIgnoreCase(details.getUsername()) && !isExpired) {
			return true;
		}
		return false;
	}

	private Boolean isTokenExpired(String jwtToken) {
		Claims claims = extractAllClaims(jwtToken);
		Date expiredDate = claims.getExpiration();
		// 10th today - expired 11th before.
		return expiredDate.before(new Date());
	}

}
