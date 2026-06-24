package com.cilazatta.frotacontrol.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "ialgli/Wtw2lsZcU8Yl3VDpge1iWJervKZeXxMGpHSw=";
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
		final Claims claims = this.extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(
	        Map<String, Object> extraClaims,
	        UserDetails userDetails) {

	    return Jwts.builder()
	            .claims(extraClaims)
	            .subject(userDetails.getUsername())
	            .issuedAt(new Date())
	            .expiration(new Date(
	                    System.currentTimeMillis()
	                            + 1000L * 60 * 60 * 24))
	            .signWith(getSignInKey())
	            .compact();
	}
	
	public String generateToken(UserDetails userDetails) {
		
		 //Usuario usuario = (Usuario) userDetails;
		
		if (!(userDetails instanceof Usuario usuario)) {
		    throw new IllegalStateException("UserDetails não é Usuario");
		}


	    Map<String, Object> claims = new HashMap<>();

	    claims.put("roles", userDetails.getAuthorities()
	        .stream()
	        .map(auth -> auth.getAuthority())
	        .toList());
	    
	    claims.put("empresaId", usuario.getFuncionario().getEmpresa().getId());
	    claims.put("denominacao", usuario.getFuncionario().getEmpresa().getNomeFantasia());


	    return generateToken(claims, userDetails);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
	    return Jwts.parser()
	            .verifyWith(getSignInKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}

	private SecretKey getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	    return Keys.hmacShaKeyFor(keyBytes);
	}

}
