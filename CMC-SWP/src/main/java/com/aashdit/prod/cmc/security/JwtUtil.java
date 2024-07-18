package com.aashdit.prod.cmc.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.service.UserApplicationMapService;
import com.aashdit.prod.cmc.service.umt.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	@Autowired
	private UserDetailsServiceImpl service;

	private String secret = "a@shd1t_k1pddb65f";
	
	@Autowired
    private UserApplicationMapService userMapService;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		List<String> modules = userMapService.getAccessableModule(username);
		return createToken(claims, username,modules);
	}

	private String createToken(Map<String, Object> claims, String username,List<String> modules) {
		UserDetails userDetails = service.loadUserByUsername(username);
		return Jwts.builder().setClaims(claims).setSubject(username)
				.claim("authorities",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.claim("modules", modules)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1hr
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Boolean validateToken(String token, User user) {
		final String username = extractUsername(token);
		return (username.equals(user.getUserName()) && !isTokenExpired(token));
	}

	public String generateTokenForModule(String json) {
		Map<String, Object> claims = new HashMap<>();
		List<String> modules=new ArrayList<>();
		return createModuleToken(claims, json,modules);
	}
	
	private String createModuleToken(Map<String, Object> claims, String json,List<String> modules) {
		return Jwts.builder().setClaims(claims).setSubject(json)
				.claim("modules", modules)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1hr
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
}
